package com.cleanChoice.cleanChoice.domain.openAi.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OpenAiResponseDtoText {
    PRODUCT_MARKET(
            "class ProductMarketLLMResponseDto {\n" +
                    "@Schema(description = \"제품 영어명\", example = \"B-2 100mg\")\n" +
                    "private String name;\n" +
//                    "@Schema(description = \"제품 성분 정보 리스트\")\n" +
//                    "private List<ProductIngredientJoinLLMResponseDto> productIngredientJoinLLMResponseDtoList;\n" +
                    "@Schema(description = \"제품 제조사명\", example = \"Vitamin World\")\n" +
                    "private String brandName;\n" +
                    "@Schema(description = \"제조국\", example = \"USA\")\n" +
                    "private String madeInCountry;\n" +
                    "@Schema(description = \"제품 총량(영어)\", example = \"100 Easy to Swallow Coated Tablet(s)\")\n" +
                    "private String englishNetContent;\n" +
                    "@Schema(description = \"제품 총량(한글)\", example = \"100정\")\n" +
                    "private String koreanNetContent;\n" +
                    "@Schema(description = \"1회 제공량\", example = \"1 tsp [3 g-9 g]\")\n" +
                    "private String servingSize;\n" +
                    "@Schema(description = \"제품 종류 (영어)\", example = \"Non-Nutrient/Non-Botanical\")\n" +
                    "private String englishProductType;\n" +
                    "@Schema(description = \"제품 종류 (한글)\", example = \"비영양/비식물성\")\n" +
                    "private String koreanProductType;\n" +
                    "@Schema(description = \"섭취 형태 (영어)\", example = \"Tablet or Pill\")\n" +
                    "private String englishSupplementForm;\n" +
                    "@Schema(description = \"섭취 형태 (한글)\", example = \"정제 알약\")\n" +
                    "private String koreanSupplementForm;\n" +
                    "@Schema(description = \"권장 복용법(영어)\", example = \"For adults, take one (1) tablet daily, preferably with a meal.\")\n" +
                    "private String englishSuggestedUse;\n" +
                    "@Schema(description = \"권장 복용법(한글)\", example = \"성인은 하루 한 정을 식사와 함께 섭취하십시오.\")\n" +
                    "private String koreanSuggestedUse;\n" +
                    "@Schema(description = \"기타 성분(영어)\", example = \"Dicalcium Phosphate, Vegetable Cellulose, Vegetable Stearic Acid, Silica, Vegetable Magnesium Stearate.\")\n" +
                    "private String englishOtherIngredients;\n" +
                    "@Schema(description = \"기타 성분(한글)\", example = \"이산화칼슘, 식물 섬유소, 식물 스테아릭산, 실리카, 식물 마그네슘 스테아레이트.\")\n" +
                    "private String koreanOtherIngredients;\n" +
//                    "@Schema(description = \"라벨 문구 리스트\")\n" +
//                    "private List<ProductLabelStatementLLMResponseDto> productLabelStatementLLMResponseDtoList;\n" +
                    "@Schema(description = \"상품 메인 이미지 url\")\n" +
                    "private String imageUrl;\n" +
                    "@Schema(description = \"상품 가격\", example = \"10000\")\n" +
                    "private Long price;\n" +
                    "@Schema(description = \"상품 가격 단위\", example = \"USD\")\n" +
                    "private String priceUnit;}",
            //"Please give me the information about the main product on this website in accordance with the Spring DTO below. It should be the information about the main product and only give me the json.\nSchema is an example, so just refer to it and provide only the information on the website or the information on the photos on the website"
            "이 웹사이트에 있는 메인 상품에 대한 정보를 아래 Spring DTO에 맞게 json을 줘 메인 상품에 대한 정보여야만 하고 json만 줘.\n" +
                    "Schema는 예시니까 참고만하고 웹사이트에 있는 정보 혹은 웹사이트에 있는 사진에 있는 정보로만 제공해줘 만약 웹사이트에 없는 정보면 필드를 null로 줘."
    ),

    PRODUCT_INGREDIENT_JOIN(
            "class ProductIngredientJoinLLMResponseDto {\n" +
                    "@Schema(description = \"성분 카테고리(영어)\", example = \"Vitamins\")\n" +
                    "private String englishCategory;\n" +
                    "@Schema(description = \"성분 카테고리(한글)\", example = \"비타민\")\n" +
                    "private String koreanCategory;\n" +
                    "@Schema(description = \"성분명(영어)\", example = \"Vitamin C\")\n" +
                    "private String englishName;\n" +
                    "@Schema(description = \"성분명(한글)\", example = \"비타민 C\")\n" +
                    "private String koreanName;\n" +
                    "@Schema(description = \"효능\", example = \"비타민 C는 강력한 항산화제로 알려져 있으며, 피부 건강과 면역 체계를 지원합니다.\")\n" +
                    "private String effectiveness;\n" +
                    "@Schema(description = \"해당 성분 주의사항\", example = \"비타민 C는 과다 복용 시 설사, 구토, 복통, 두통, 구역, 피로감, 구역 증상이 나타날 수 있습니다.\")\n" +
                    "private String caution;\n" +
                    "@Schema(description = \"1회 복용양\", example = \"1.0\")\n" +
                    "private Double servingSize;\n" +
                    "@Schema(description = \"1회 복용양 단위\", example = \"Tablet(s)\")\n" +
                    "private String servingUnit;\n" +
                    "@Schema(description = \"복용 당 성분 섭취량\", example = \"100.0\")\n" +
                    "private Double amountPerServing;\n" +
                    "@Schema(description = \"복용 당 성분 섭취량 단위\", example = \"mg\")\n" +
                    "private String amountPerServingUnit;\n" +
                    "@Schema(description = \"일일 권장 섭취량 대비 비율(%)\", example = \"5882.0\")\n" +
                    "private Double dailyValuePerServing;\n" +
                    "@Schema(description = \"일일 권장 섭취량 기준 그룹\", example = \"Adults and children 4 or more years of age\")\n" +
                    "private String englishDailyValueTargetGroup;\n" +
                    "@Schema(description = \"일일 권장 섭취량 기준 그룹\", example = \"4세 이상 어린이 및 성인\")\n" +
                    "private String koreanDailyValueTargetGroup;}",
            //"Please give me the information on the main product on this website as a list in accordance with the Spring DTO below. It must be the information on the main product and only give me the json.\nSchema is an example, so just refer to it and provide only the information on the website or the information on the photos on the website"
            "이 웹사이트에 있는 메인 상품에 대한 정보를 아래 Spring DTO에 맞게 json 리스트 줘 메인 상품에 대한 정보여야만 하고 json만 줘.\n" +
                    "Schema는 예시니까 참고만하고 웹사이트에 있는 정보 혹은 웹사이트에 있는 사진에 있는 정보로만 제공해줘 만약 웹사이트에 없는 정보면 필드를 null로 줘."
    ),

    PRODUCT_LABEL_STATEMENT(
            "class ProductLabelStatementLLMResponseDto {\n" +
                    "@Schema(description = \"라벨 문구(영어)\", example = \"Non-GMO\")\n" +
                    "private String englishLabelStatement;\n" +
                    "@Schema(description = \"라벨 문구(한글)\", example = \"비유전자변형\")\n" +
                    "private String koreanLabelStatement;}",
            //"Please give me the information on the main product on this website as a list in accordance with the Spring DTO below. It must be the information on the main product and only give me the json.\nSchema is an example, so just refer to it and provide only the information on the website or the information on the photos on the website"
            "이 웹사이트에 있는 메인 상품에 대한 정보를 아래 Spring DTO에 맞게 json 리스트를 줘 메인 상품에 대한 정보여야만 하고 json만 줘.\n" +
                    "Schema는 예시니까 참고만하고 웹사이트에 있는 정보 혹은 웹사이트에 있는 사진에 있는 정보로만 제공해줘 만약 웹사이트에 없는 정보면 필드를 null로 줘."
    );

    private final String text;
    private final String prompt;
}
