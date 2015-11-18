package org.agents.simulator.organisations.messages;

import madkit.kernel.Message;

public class SlaveryMessage extends Message{

    public final Type type;
    private Object data;

    public Object getData() {
        return data;
    }

    public SlaveryMessage(Type type) {
        this.type = type;
    }

    public SlaveryMessage(Object data) {
        this(Type.RESULT);
        this.data = data;
    }

    public enum Type {
        WORK,
        DIE,
        RESULT
    }
}
