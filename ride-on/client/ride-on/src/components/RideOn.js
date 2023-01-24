import React from 'react';
import '../styles/rideOn.css';

function RideOn() {
  return (
    <>
    <h1>Welcome to Ride On!!</h1>

    <img className="img-fluid mt-5 rounded mb-5" src={process.env.PUBLIC_URL + "/images/jeep.gif"} alt="Keep on Riding Baby!" 
    style ={{width:'100%', height:'100%', backgroundSize:'cover'}} />
    
    </>
  )
}

export default RideOn;