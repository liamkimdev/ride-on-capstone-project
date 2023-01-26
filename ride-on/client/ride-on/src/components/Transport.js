import { React, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Transport({ currentUser, cars, setCars, }) {

  const navigate = useNavigate();

  const changeBackground = () => {
    const rootElement = document.getElementById("root");
    rootElement.style.background = `url(${process.env.PUBLIC_URL + '/images/jeep.gif'}) repeat-y center fixed`;
    rootElement.style.backgroundSize = "cover";
    rootElement.style.height = "150vh";
  }

  useEffect(() => {
    changeBackground();
  }, []);

  return (
    <>
      <div className="container pt-5 mt-5">
        <div className="row text-center bubble-box text slide-right">
          <div className="col-12">
            <h4 className="mb-5">Your pick of rides at low prices</h4>
          </div>

          <div className="col-md-6 col-xl-4 offset-xl-2 mb-5">
            <img src="images/car-solid.svg" alt="Drive"
              className="drive-img"
              onClick={() => {
                if (!currentUser) {
                  navigate("/signin");
                }
                console.log(currentUser.cars.length); // currentCar.userId
                if (currentUser.cars.length > 0) {
                  navigate("/api/ride_on/trip/form");
                } else {
                  navigate("/api/ride_on/car/form");
                }
              }} />
            <p
              className="drive-text text"
              onClick={() => {
                navigate("/api/ride_on/trip")
              }}
            >Post a Ride?</p>
          </div>

          <div className="col-md-6 col-xl-4 mb-5">
            <img src="images/thumbs-up-solid.svg" alt="Ride"
              className="ride-img"
              onClick={() => {
                navigate("/api/ride_on/trip")
              }}
            />

            <p
              className="ride-text text"
              onClick={() => {
                navigate("/api/ride_on/trip")
              }}
            >Book a Ride!</p>
          </div>
        </div>
        <br></br>
          <br></br>
          <br></br>
      
      </div>
    </>
  );
}

export default Transport;
