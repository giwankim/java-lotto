package lotto.controller;

import lotto.domain.model.LottoMoney;
import lotto.domain.model.LottoResult;
import lotto.domain.model.LottoTicket;
import lotto.domain.model.LottoTickets;
import lotto.domain.service.LottoService;
import lotto.view.InputView;
import lotto.view.ResultView;

public class LottoController {

    private final LottoTickets userTickets;
    private final LottoTicket winningTicket;
    private final LottoMoney lottoMoney;

    private LottoController(LottoTickets userTickets, LottoTicket winningTicket,
            LottoMoney lottoMoney) {
        this.userTickets = userTickets;
        this.winningTicket = winningTicket;
        this.lottoMoney = lottoMoney;
    }

    public static LottoController fromUserInput() {
        LottoMoney lottoMoney = lottoMoneyInput();
        LottoTickets randomTickets = LottoService.createRandomTickets(lottoMoney);
        ResultView.printLottoTickets(randomTickets);
        LottoTicket winningTicket = winningLottoFromInput();
        return new LottoController(randomTickets, winningTicket, lottoMoney);
    }

    private static LottoMoney lottoMoneyInput() {
        int amount = InputView.askHowMuch();
        return LottoMoney.of(amount);
    }

    private static LottoTicket winningLottoFromInput() {
        String text = InputView.askForWinningLotto();
        return LottoTicket.of(text);
    }

    public void run() {
        LottoResult lottoResult = userTickets.match(winningTicket);
        ResultView.printLottoResult(lottoResult);
        double profitPercent = LottoService.profitPercent(lottoResult, lottoMoney);
        ResultView.printProfitPercent(profitPercent);
    }
}
