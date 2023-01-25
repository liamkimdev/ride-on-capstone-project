import { useEffect } from 'react';
import '../styles/rideOn.css';

function Home() {

  return (
    <>
      <div className="text-center pt-5 mt-5 col align-self-center">
        <h1 className="slide-right"
        style={{fontFamily: "cutestThings", fontSize: "50px", color: "#4A7BDE", textShadow: "2px 2px 2px #FFFFFF" }}>Welcome to Ride On!</h1>
      </div>

      {/* <img src={process.env.PUBLIC_URL + "/images/jeep.gif"} alt="Keep on Riding Baby!" /> */}
    </>
  )
}

export default Home;