function Message({ message, messages, setMessages }) {

    const handleClose = () => {
        let filteredMessages = messages.filter(m => m.id !== message.id);
        setMessages(filteredMessages);
    }

    setTimeout(() => {
        handleClose();
    }, 6543)

    return (
        <div className="container pt-2 mt-2 text-center col-sm-12 col-md-10 offset-md-1 col-lg-8 offset-lg-2 col-xl-6 offset-xl-3">
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
        </div>
    )
}

export default Message;