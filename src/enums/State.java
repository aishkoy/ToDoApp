package enums;

public enum State {
    NEW("Новая") {
        @Override
        void deleteTak() {

        }

        @Override
        void changeState() {

        }
    },
    IN_PROGRESS("В процессе") {
        @Override
        void deleteTak() {

        }

        @Override
        void changeState() {

        }
    },
    DONE("Сделано") {
        @Override
        void deleteTak() {

        }

        @Override
        void changeState() {

        }
    };

    private String stateStr;

    State(String StateStr) {
        this.stateStr = StateStr;
    }

    abstract void deleteTak();
    abstract void changeState();
}
