package store.utility;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileUtilityTest {

    private static final String INITIAL_PROMOTION_FILE_NAME = "src/test/resources/promotions.md";

    @DisplayName("공백을 기준으로 파일 읽기")
    @Test
    void 공백을_기준으로_파일_읽기() {
        List<String> texts = FileUtility.readFileBySpace(INITIAL_PROMOTION_FILE_NAME);

        assertThat(texts.size()).isEqualTo(4);
        assertThat(texts.getFirst()).isEqualTo("name,buy,get,start_date,end_date");
        assertThat(texts.getLast()).isEqualTo("반짝할인,1,1,2024-11-01,2024-11-30");
    }
}