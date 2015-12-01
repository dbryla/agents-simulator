package org.agents.simulator.organisations.messages;

import madkit.kernel.Message;

public class CommonMessage extends Message{

    public final Type type;
    private Object data;

    public Object getData() {
        return data;
    }

    public CommonMessage(Type type) {
        this.type = type;
    }

    public CommonMessage(Object data) {
        this(Type.RESULT);
        this.data = data;
    }

    public enum Type {
        WORK,
        DIE,
        RESULT
    }
}
