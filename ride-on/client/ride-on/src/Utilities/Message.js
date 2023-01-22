function Message({ message, messages, setMessages }) {

    const handleClose = () => {
        let filteredMessages = messages.filter(m => m.id !== message.id);
        setMessages(filteredMessages);        
    }

    setTimeout(()=>{
        handleClose();
    }, 5000)

    return (
        <div className={message.type === "success" ? "alert alert-success" : "alert alert-danger"}>
            <div className="row">
                <div className="col-11">
                    <p className="mb-0">{message.text}</p>
                </div>
                <div className="col-1 text-end">
                    <button type="button" className="btn-close" aria-label="Close" onClick={handleClose}></button>
                </div>
            </div>
        </div>
    )
}

export default Message;