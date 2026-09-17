import java.util.*;


/**
 * Class that imitates game. It runs dealer and player and read/write input/output in console.
 */
public class Engine {
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;
    private int playerScore;
    private int dealerScore;
    private int roundNumber;


    /**
     * Sets all objects to start and all counters to start.
     */
    public Engine() {
        this.deck = new Deck();
        this.player = new Player();
        this.dealer = new Dealer();
        this.playerScore = 0;
        this.dealerScore = 0;
        this.roundNumber = 1;
    }

    /**
     * Starts/ends the game.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в Блэкджек!");

        while (true) {
            System.out.println("\nРаунд " + roundNumber);
            playRound(scanner);
            roundNumber++;

            System.out.print("\nХотите сыграть еще раунд? (1 - Да, 0 - Нет): ");
            String choice = scanner.nextLine().trim();
            if (!choice.equals("1")) {
                System.out.println("Спасибо за игру! Итоговый счет: " + playerScore + ":" + dealerScore);
                break;
            }
        }
    }

    /**
     * Method for playing a round.
     * @param scanner scanner that scans input.
     */
    private void playRound(Scanner scanner) {
        player.clearHand();
        dealer.clearHand();

        player.addCard(deck.pickCard());
        dealer.addCard(deck.pickCard());
        player.addCard(deck.pickCard());
        dealer.addCard(deck.pickCard());

        System.out.println("Дилер раздал карты");
        printTableState(true);

        boolean playerBJ = player.hasBlackjack();
        boolean dealerBJ = dealer.hasBlackjack();

        if (playerBJ || dealerBJ) {
            System.out.println("\nДилер открывает закрытую карту " + dealer.getHand().get(1));
            printTableState(false);
            if (playerBJ && dealerBJ) {
                System.out.println("У обоих блэкджек! Ничья.");
            } else if (playerBJ) {
                playerScore++;
                System.out.println("Блэкджек! Вы выиграли раунд! Счет " + playerScore + ":" + dealerScore + " в вашу пользу.");
            } else {
                dealerScore++;
                System.out.println("У дилера блэкджек! Дилер выиграл раунд! Счет " + playerScore + ":" + dealerScore + ".");
            }
            return;
        }

        System.out.println("\nВаш ход");
        System.out.println("-------");
        boolean playerBusted = false;

        while (true) {
            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");
            String input = scanner.nextLine().trim();
            if (input.equals("1")) {
                Card newCard = deck.pickCard();
                player.addCard(newCard);
                System.out.println("Вы открыли карту " + newCard);
                printTableState(true);

                if (player.isBust()) {
                    playerBusted = true;
                    break;
                }
            } else if (input.equals("0")) {
                break;
            } else {
                System.out.println("Неверный ввод. Введите 1 или 0.");
            }
        }

        if (playerBusted) {
            dealerScore++;
            System.out.println("\nУ вас перебор! Дилер выиграл раунд! Счет " + playerScore + ":" + dealerScore + ".");
            return;
        }

        System.out.println("\nХод дилера");
        System.out.println("-------");
        System.out.println("Дилер открывает закрытую карту " + dealer.getHand().get(1));
        printTableState(false);

        while (dealer.getScore() < 17) {
            Card newCard = deck.pickCard();
            dealer.addCard(newCard);
            System.out.println("\nДилер открывает карту " + newCard);
            printTableState(false);
        }

        if (dealer.isBust()) {
            playerScore++;
            System.out.println("\nУ дилера перебор! Вы выиграли раунд! Счет " + playerScore + ":" + dealerScore + " в вашу пользу.");
        } else {
            int pScore = player.getScore();
            int dScore = dealer.getScore();

            if (pScore > dScore) {
                playerScore++;
                System.out.println("\nВы выиграли раунд! Счет " + playerScore + ":" + dealerScore + " в вашу пользу.");
            } else if (dScore > pScore) {
                dealerScore++;
                System.out.println("\nДилер выиграл раунд! Счет " + playerScore + ":" + dealerScore + ".");
            } else {
                System.out.println("\nНичья! Счет " + playerScore + ":" + dealerScore + ".");
            }
        }
    }

    /**
     * Prints current state between rounds.
     * @param hideDealerCard contains info about hidden dealer's card.
     */
    private void printTableState(boolean hideDealerCard) {
        System.out.println("    Ваши карты: " + player.getFormattedHand());
        if (hideDealerCard) {
            System.out.println("    Карты дилера: " + dealer.getFormattedHandHidden());
        } else {
            System.out.println("    Карты дилера: " + dealer.getFormattedHand());
        }
    }
}