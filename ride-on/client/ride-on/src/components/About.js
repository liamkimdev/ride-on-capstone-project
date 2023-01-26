import { useEffect } from "react";

function About() {

  const changeBackground = () => {
    const rootElement = document.getElementById("root");
    rootElement.style.background = `url(${process.env.PUBLIC_URL + '/images/jeep.gif'}) repeat-y center fixed`;
    rootElement.style.backgroundSize = "cover";
    rootElement.style.height = "100%";
  }

  useEffect(() => {
    changeBackground();
  }, []);

  
  return (
    <div className="container mt-5 pt-5">
    <div className="text slide-right">

      <div
      style={{ fontFamily: "cutestThings", fontSize: "50px", color: "#4A7BDE", textShadow: "2px 2px 2px #FFFFFF" }}>
        <h1 className="text-center"><strong>What is Ride On?</strong></h1>
      </div>

      <br></br>

      <div className="bubble-box">
        <p className="text-center">
          <h4><strong>What is the primary goal of this application</strong>?</h4>
        </p>

        <p><strong>
        "Ride On" aimes to instill faith and trust in the community by connecting drivers of everyday trips with passengers looking for a ride to the same destination. All while saving money and resources, reducing traffic congestion, limiting environmental impacts, and fostering friendships along the way.
        </strong> </p>

        <h5 className="text-center">
          Future Goal: connect company employees and university students
        </h5>
      </div>

      <br></br>

      <div className="bubble-box">
        <p className="text-center">
          <h4><strong>What are the features of this application?</strong></h4>
        </p>
        <p className="text-center"><strong> “Ride On” is an online marketplace for carpooling</strong>.</p><br/>

         <p>Its website and mobile apps connect drivers and passengers willing to travel together between cities and share the journey cost. </p>
         <p>The company does not own any vehicle.</p> 
         <p>This application makes it fun and affordable to carpool from city to city in the United States of America. </p>
         <h5 className="text-center">Save money, reduce your carbon footprint, and make new friends.</h5>
      </div>

      <br/>

      <div className="bubble-box">
        <p className="text-center">
         <h4><strong>Safety concerns?</strong></h4>
        </p>

        <p className="text-center"><strong>Safety is our number one priority</strong></p>

        <p>
        , such as verifying users via identification, choosing who they travel with, active admin monitoring of trips through GPS tracking, a guarantee that drivers are registured and insured, and more!
        </p>
        <h5 className="text-center">We go the extra mile to ensure members have a safe experience using “Ride On”</h5>
      </div>
    </div>
    <br/>
      <div className="bubble-box">
        <p className="text-center">
          <h4><strong>How is it different from Uber and Lyft?</strong></h4>
        </p>

        <p>
          Uber and Lyft focus on an on-demand type of business model where you
          request a ride and someone with a car will accommodate to your
          desires, in other words the passenger is the one who runs the show
          and the driver follows along. With Ride on, the driver is the one
          making the decision on where to go and the passenger tags along.
        </p>

        <h5 className="text-center">And most importantly, much cheaper than Uber and Lyft!</h5>
      </div>
      <br/>
    </div>
  );
}

export default About;