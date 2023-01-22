import Message from "./Message";

function MessageFactory({ messages, setMessages }) {

    const messageCreator = () => {
        if (messages.length > 0) {
            return messages.map(message => {
                return <Message message={message} 
                                messages={messages} 
                                setMessages={setMessages} 
                                key={message.text} 
                        />
            })
        }
    }

    return messageCreator();
}

export default MessageFactory;