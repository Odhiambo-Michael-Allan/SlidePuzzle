import javafx.application.Platform;

public class StatsViewController implements OptionViewListener {

    private StatsView statsView;

    public StatsViewController( StatsView statsView ) {
        this.statsView = statsView;
        new TimeThread().start();
    }
    @Override
    public void newGame() {
        Platform.runLater( () -> statsView.getTotalMovesMade().setText( "Total moves: " + 0 ) );
    }

    @Override
    public void quit() {}

    @Override
    public void solve() {
        Platform.runLater( () -> statsView.getTotalMovesMade().setText( String.format( "Total moves: %d", 0 ) ) );
    }

    @Override
    public void reset() {
        Platform.runLater( () -> statsView.getTotalMovesMade().setText( String.format( "Total moves: %d", 0 ) ) );
    }

    private class TimeThread extends Thread {

        int seconds, minutes, hours;
        TimeThread() {
            setDaemon( true );
            this.seconds = 0;
            this.minutes = 0;
            this.hours = 0;
        }

        public void run() {
            while ( true ) {
                updateTimer();
                Platform.runLater( () -> statsView.getElapsedTime().setText( String.format(
                        "Elapsed: %02d:%02d:%02d", hours, minutes, seconds ) ) );
                try {
                    Thread.sleep( 1000 );
                } catch ( InterruptedException e ) {}
            }
        }

        private void updateTimer() {
            seconds++;
            if ( seconds >= 60 ) {
                minutes++;
                seconds = 0;
            }
            if ( minutes >= 60 ) {
                hours++;
                minutes = 0;
            }
        }
    }
}
