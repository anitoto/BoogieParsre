package towzer.preferences;

/**
 */
public class ConfigParam<T> {
    private final T defaultValue;
    private T value;
    private boolean isUserSet;

    public ConfigParam(T defaultValue) {
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.isUserSet = false;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
        this.isUserSet = true;
    }

    public T getDefault() {
        return defaultValue;
    }

    public boolean isUserSet() {
        return isUserSet;
    }

    public void reset() {
        this.value = defaultValue;
        this.isUserSet = false;
    }

    @Override
    public String toString() {
        return String.format("Value: %s (default: %s, userSet: %s)", value, defaultValue, isUserSet);
    }
}
