package com.soufflet.mobile.sunshine.adapter.weather;

import com.google.common.collect.ImmutableList;

import org.junit.Test;

import static com.soufflet.mobile.sunshine.adapter.weather.WeatherDataParser.getWeatherDataFromJson;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class WeatherDataParserTest {

    private static final String TEST_JSON_DATA =
            "{\n" +
                    "   \"city\":{\n" +
                    "      \"id\":5375480,\n" +
                    "      \"name\":\"Mountain View\",\n" +
                    "      \"coord\":{\n" +
                    "         \"lon\":-122.083847,\n" +
                    "         \"lat\":37.386051\n" +
                    "      },\n" +
                    "      \"country\":\"US\",\n" +
                    "      \"population\":0\n" +
                    "   },\n" +
                    "   \"cod\":\"200\",\n" +
                    "   \"message\":0.3501,\n" +
                    "   \"cnt\":7,\n" +
                    "   \"list\":[\n" +
                    "      {\n" +
                    "         \"dt\":1478372400,\n" +
                    "         \"temp\":{\n" +
                    "            \"day\":18.24,\n" +
                    "            \"min\":7.12,\n" +
                    "            \"max\":21.27,\n" +
                    "            \"night\":7.77,\n" +
                    "            \"eve\":19.14,\n" +
                    "            \"morn\":8.26\n" +
                    "         },\n" +
                    "         \"pressure\":994.53,\n" +
                    "         \"humidity\":74,\n" +
                    "         \"weather\":[\n" +
                    "            {\n" +
                    "               \"id\":500,\n" +
                    "               \"main\":\"Rain\",\n" +
                    "               \"description\":\"light rain\",\n" +
                    "               \"icon\":\"10d\"\n" +
                    "            }\n" +
                    "         ],\n" +
                    "         \"speed\":1.09,\n" +
                    "         \"deg\":284,\n" +
                    "         \"clouds\":0\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"dt\":1478458800,\n" +
                    "         \"temp\":{\n" +
                    "            \"day\":14.85,\n" +
                    "            \"min\":8.47,\n" +
                    "            \"max\":18.9,\n" +
                    "            \"night\":8.47,\n" +
                    "            \"eve\":18.28,\n" +
                    "            \"morn\":8.92\n" +
                    "         },\n" +
                    "         \"pressure\":995.13,\n" +
                    "         \"humidity\":88,\n" +
                    "         \"weather\":[\n" +
                    "            {\n" +
                    "               \"id\":500,\n" +
                    "               \"main\":\"Rain\",\n" +
                    "               \"description\":\"light rain\",\n" +
                    "               \"icon\":\"10d\"\n" +
                    "            }\n" +
                    "         ],\n" +
                    "         \"speed\":1.21,\n" +
                    "         \"deg\":21,\n" +
                    "         \"clouds\":48,\n" +
                    "         \"rain\":0.31\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"dt\":1478545200,\n" +
                    "         \"temp\":{\n" +
                    "            \"day\":16.8,\n" +
                    "            \"min\":5.85,\n" +
                    "            \"max\":20.46,\n" +
                    "            \"night\":9,\n" +
                    "            \"eve\":19.89,\n" +
                    "            \"morn\":7.16\n" +
                    "         },\n" +
                    "         \"pressure\":996.69,\n" +
                    "         \"humidity\":78,\n" +
                    "         \"weather\":[\n" +
                    "            {\n" +
                    "               \"id\":800,\n" +
                    "               \"main\":\"Clear\",\n" +
                    "               \"description\":\"clear sky\",\n" +
                    "               \"icon\":\"02d\"\n" +
                    "            }\n" +
                    "         ],\n" +
                    "         \"speed\":1.52,\n" +
                    "         \"deg\":76,\n" +
                    "         \"clouds\":8\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"dt\":1478631600,\n" +
                    "         \"temp\":{\n" +
                    "            \"day\":16.33,\n" +
                    "            \"min\":9.21,\n" +
                    "            \"max\":19.36,\n" +
                    "            \"night\":12.22,\n" +
                    "            \"eve\":19.36,\n" +
                    "            \"morn\":9.21\n" +
                    "         },\n" +
                    "         \"pressure\":1014.9,\n" +
                    "         \"humidity\":0,\n" +
                    "         \"weather\":[\n" +
                    "            {\n" +
                    "               \"id\":800,\n" +
                    "               \"main\":\"Clear\",\n" +
                    "               \"description\":\"clear sky\",\n" +
                    "               \"icon\":\"01d\"\n" +
                    "            }\n" +
                    "         ],\n" +
                    "         \"speed\":1.73,\n" +
                    "         \"deg\":53,\n" +
                    "         \"clouds\":1\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"dt\":1478718000,\n" +
                    "         \"temp\":{\n" +
                    "            \"day\":16.82,\n" +
                    "            \"min\":10.26,\n" +
                    "            \"max\":18.84,\n" +
                    "            \"night\":13.24,\n" +
                    "            \"eve\":18.84,\n" +
                    "            \"morn\":10.26\n" +
                    "         },\n" +
                    "         \"pressure\":1016.96,\n" +
                    "         \"humidity\":0,\n" +
                    "         \"weather\":[\n" +
                    "            {\n" +
                    "               \"id\":500,\n" +
                    "               \"main\":\"Rain\",\n" +
                    "               \"description\":\"light rain\",\n" +
                    "               \"icon\":\"10d\"\n" +
                    "            }\n" +
                    "         ],\n" +
                    "         \"speed\":2.38,\n" +
                    "         \"deg\":26,\n" +
                    "         \"clouds\":71,\n" +
                    "         \"rain\":0.47\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"dt\":1478804400,\n" +
                    "         \"temp\":{\n" +
                    "            \"day\":16.57,\n" +
                    "            \"min\":10.46,\n" +
                    "            \"max\":20.44,\n" +
                    "            \"night\":12.81,\n" +
                    "            \"eve\":20.44,\n" +
                    "            \"morn\":10.46\n" +
                    "         },\n" +
                    "         \"pressure\":1017.92,\n" +
                    "         \"humidity\":0,\n" +
                    "         \"weather\":[\n" +
                    "            {\n" +
                    "               \"id\":800,\n" +
                    "               \"main\":\"Clear\",\n" +
                    "               \"description\":\"clear sky\",\n" +
                    "               \"icon\":\"01d\"\n" +
                    "            }\n" +
                    "         ],\n" +
                    "         \"speed\":2.14,\n" +
                    "         \"deg\":48,\n" +
                    "         \"clouds\":0\n" +
                    "      },\n" +
                    "      {\n" +
                    "         \"dt\":1478890800,\n" +
                    "         \"temp\":{\n" +
                    "            \"day\":16.81,\n" +
                    "            \"min\":10.51,\n" +
                    "            \"max\":20.04,\n" +
                    "            \"night\":12.12,\n" +
                    "            \"eve\":20.04,\n" +
                    "            \"morn\":10.51\n" +
                    "         },\n" +
                    "         \"pressure\":1017.05,\n" +
                    "         \"humidity\":0,\n" +
                    "         \"weather\":[\n" +
                    "            {\n" +
                    "               \"id\":800,\n" +
                    "               \"main\":\"Clear\",\n" +
                    "               \"description\":\"clear sky\",\n" +
                    "               \"icon\":\"01d\"\n" +
                    "            }\n" +
                    "         ],\n" +
                    "         \"speed\":1.74,\n" +
                    "         \"deg\":57,\n" +
                    "         \"clouds\":0\n" +
                    "      }\n" +
                    "   ]\n" +
                    "}";

    @Test
    public void whenGetWeatherDataFromJson_thenReturnExpectedValues() throws Exception {
        // WHEN
        ImmutableList<String> data = getWeatherDataFromJson(TEST_JSON_DATA);

        // THEN
        assertThat(data.size(), equalTo(7));
        assertThat(data.get(0), equalTo("Sat Nov 05 - Rain - 21/7"));
    }
}