package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LottoTicketTest {

    private LottoTicket lottoTicket;

    @BeforeEach
    void setUp() {
        lottoTicket = LottoTicket.createFromList(Arrays.asList(1, 2, 3, 4, 5, 6));
    }

    @Test
    @DisplayName("6개 보다 적거나 많은 숫자를 전달하면 IllegalArgumentException 예외를 throw 한다.")
    void createFromList_out_of_bounds() {
        List<Integer> tooManyNumbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        assertThatThrownBy(() -> LottoTicket.createFromList(tooManyNumbers)).isInstanceOf(
                IllegalArgumentException.class);

        List<Integer> tooFewNumbers = Arrays.asList(1, 2, 3, 4, 5);
        assertThatThrownBy(() -> LottoTicket.createFromList(tooFewNumbers)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    @DisplayName("반복된 숫자로 생성자를 호출하면 IllegalArgumentException 예외를 throw 한다.")
    void createFromList_repeated_numbers() {
        List<Integer> numbers = Arrays.asList(1, 1, 3, 4, 5, 6);
        assertThatThrownBy(() -> LottoTicket.createFromList(numbers)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Test
    @DisplayName("번호가 로또에 포함되어 있다.")
    void contains_number_in_ticket() {
        LottoNumber lottoNumber = LottoNumber.valueOf(6);
        assertThat(lottoTicket.contains(lottoNumber)).isTrue();
    }

    @Test
    @DisplayName("번호가 로또에 포함되자 않았다.")
    void contains_number_not_in_ticket() {
        LottoNumber lottoNumber = LottoNumber.valueOf(8);
        assertThat(lottoTicket.contains(lottoNumber)).isFalse();
    }

    @Test
    @DisplayName("로또에 당첨 번호에 포함된 숫자가 없다.")
    void findRank_miss() {
        LottoTicket winningTicket = LottoTicket.createFromList(Arrays.asList(7, 8, 9, 10, 11, 12));
        Rank rank = lottoTicket.findRank(winningTicket);
        assertThat(rank).isEqualTo(Rank.MISS);
    }

    @Test
    @DisplayName("로또가 당첨 번호와 일치한다.")
    void findRank_first() {
        LottoTicket winningTicket = LottoTicket.createFromList(Arrays.asList(1, 2, 3, 4, 5, 6));
        Rank rank = lottoTicket.findRank(winningTicket);
        assertThat(rank).isEqualTo(Rank.FIRST);
    }
}
