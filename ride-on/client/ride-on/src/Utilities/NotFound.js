import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function NotFound() {

    const navigate = useNavigate();

    useEffect(()=>{
        setTimeout(()=>{
            navigate("/");
        }, 5000)
    }, [])

    return (
        <>
            <div className="row text-center mt-4">
                <h3>Sorry, the page you requested cannot be found.</h3>
                <div className="col-6 offset-3 mt-3">
                    <img className="img-fluid" src={process.env.PUBLIC_URL + "/images/404.svg"} alt="404 Not Found" />
                </div>
                <p className="h5">You will be returned to the homepage in 5 seconds...</p>
            </div>
        </>
    )
}

export default NotFound;