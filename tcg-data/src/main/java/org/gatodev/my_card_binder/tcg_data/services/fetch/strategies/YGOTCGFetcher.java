package org.gatodev.my_card_binder.tcg_data.services.fetch.strategies;

import lombok.extern.slf4j.Slf4j;
import org.gatodev.my_card_binder.enums.TCG;
import org.gatodev.my_card_binder.tcg_data.dto.response.CardImageYGOPro;
import org.gatodev.my_card_binder.tcg_data.dto.response.CardYGOPro;
import org.gatodev.my_card_binder.tcg_data.dto.response.SetYGOPro;
import org.gatodev.my_card_binder.tcg_data.entities.Card;
import org.gatodev.my_card_binder.tcg_data.entities.CardSet;
import org.gatodev.my_card_binder.tcg_data.services.fetch.TCGFetcherStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class YGOTCGFetcher implements TCGFetcherStrategy {
    private final WebClient webClient;

    public YGOTCGFetcher() {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs()
                        .maxInMemorySize(50 * 1024 * 1024))
                .build();

        this.webClient = WebClient.builder()
                .baseUrl("https://db.ygoprodeck.com/api/v7")
                .exchangeStrategies(exchangeStrategies)
                .build();
    }

    @Override
    public List<Card> fetchCards() {
        DataCardYGOPro response = webClient.get()
                .uri("/cardinfo.php")
                .retrieve()
                .bodyToMono(DataCardYGOPro.class)
                .block();

        return convertCard(response, null);
    }

    @Override
    public List<Card> fetchCardsBySet(CardSet cardSet) {
        DataCardYGOPro response = webClient.get()
                .uri("/cardinfo.php?cardset=" + cardSet.getName())
                .retrieve()
                .bodyToMono(DataCardYGOPro.class)
                .block();

        return convertCard(response, cardSet.getName());
    }

    private record DataCardYGOPro(
            List<CardYGOPro> data
    ) {}

    private List<Card> convertCard(DataCardYGOPro response, String setName) {
        return response == null ? List.of() : response.data()
                .stream()
                .filter(ygo -> ygo.card_sets() != null)
                .flatMap(ygo -> ygo.card_sets()
                        .stream()
                        .filter(set -> setName == null || set.set_name().equals(setName))
                        .map(set -> {
                            Deque<String> typeParts = new ArrayDeque<>
                                    (Arrays.asList(ygo.humanReadableCardType().split(" ")));

                            String images = ygo.card_images()
                                    .stream()
                                    .map(CardImageYGOPro::image_url)
                                    .collect(Collectors.joining(" ~ "));

                            return Card.builder()
                                    .name(ygo.name())
                                    .code(set.set_code())
                                    .rarity(set.set_rarity())
                                    .rarityCode(set.set_rarity_code())
                                    .tcg(TCG.YUGIOH)
                                    .type(typeParts.removeLast())
                                    .subtypes(new ArrayList<>(typeParts))
                                    .archetype(ygo.archetype())
                                    .description(ygo.desc())
                                    .detailsUrl(ygo.ygoprodeck_url())
                                    .image(images)
                                    .build();
                        })
                )
                .toList();
    }

    @Override
    public List<CardSet> fetchCardSets() {
        List<SetYGOPro> response = webClient.get()
                .uri("/cardsets.php")
                .retrieve()
                .bodyToFlux(SetYGOPro.class)
                .collectList()
                .block();

        return response == null ? List.of() :
                response.stream().distinct()
                        .map(cs -> CardSet.builder()
                                .tcg(getTCG())
                                .name(cs.set_name())
                                .code(cs.set_code())
                                .releaseDate(cs.tcg_date())
                                .images(Collections.singletonList(cs.set_image()))
                                .build())
                        .toList();
    }

    @Override
    public TCG getTCG() {
        return TCG.YUGIOH;
    }
}
