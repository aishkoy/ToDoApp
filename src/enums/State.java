package enums;

public enum State {
    NEW("New") {
        @Override
        void deleteTak() {

        }

        @Override
        void changeState() {

        }
    },
    IN_PROGRESS("In Progress") {
        @Override
        void deleteTak() {

        }

        @Override
        void changeState() {

        }
    },
    DONE("Done") {
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
