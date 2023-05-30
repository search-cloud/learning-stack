package io.vincent.learning.stack.concurrency.mq;

/**
 * Message.
 *
 * @author Vincent.Lu.
 * @since 2023/5/27
 */
public class Message {
    private Long id;
    private String topic;
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String topic;
        private String body;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Message build() {
            Message message = new Message();
            message.setId(id);
            message.setTopic(topic);
            message.setBody(body);
            return message;
        }
    }
}
