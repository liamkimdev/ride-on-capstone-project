import React from "react";
import { useNavigate } from "react-router-dom";

function Transport({ currentUser, cars, setCars }) {

  const navigate = useNavigate();

  return (
    <>
      <div className="d-flex justify-content-center mb-5 text-muted">
        <h4> Your pick of rides at low prices</h4>
      </div>
      <div className="d-flex align-items-center justify-content-center mx-auto row">

        <div className="col-3">
          <img src="images/car-solid.svg" alt="Drive"
            className="drive-img"
            onClick={() => {
              // navigate to /signin page if user doesn't have an account
              if (!currentUser) {
                navigate("/signin");
              }
              // navigate to ../trip/form if user owns a car
              console.log(currentUser.cars.length); // currentCar.userId
              if (currentUser.cars.length > 0) {
                navigate("/api/ride_on/trip/form");
              } else {
                navigate("/api/ride_on/car/form");
              }
            }} />
          </div>

        <div className="col-3">
          <img src="images/chair-solid.svg" alt="Ride"
            className="ride-img"
            onClick={() => {
              navigate("/api/ride_on/trip")
            }} />
        </div>
        </div>

        
        <div className="row d-flex space-between">
          <div className="col-3 drive-text m-4">Post a Ride</div>
          
          <div className=" col-3 ride-text m-4">Book a Ride</div>

          </div>

        <div className="col-3">
        </div>


      
    </>
  );
}

export default Transport;
