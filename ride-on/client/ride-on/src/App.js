import { useEffect, useState } from "react";
import { Route, Routes, Navigate, useNavigate } from "react-router-dom";
import jwtDecode from "jwt-decode";
import Signin from "./Utilities/Signin";
import Signup from "./Utilities/Signup";
import AuthContext from "./contexts/AuthContext";
import Transport from "./components/Transport";
import Nav from "./Utilities/Nav";
import About from "./components/About";
import TripForm from "./components/TripForm";
import Home from "./components/Home";
import CarForm from "./components/CarForm";
import "./styles/rideOn.css";

import TripFactory from "./components/TripFactory";
import MessageFactory from "./Utilities/MessageFactory";

const LOCAL_STORAGE_TOKEN_KEY = "rideOnToken";

function App() {
  const [messages, setMessages] = useState([]);
  const [auth, setAuth] = useState({
    currentUser: null,
    login,
    logout,
  });
  const [cars, setCars] = useState([]);

  function login(token) {
    const {
      sub: username,
      authorities: authoritiesString,
      user_id: userId,
    } = jwtDecode(token);

    localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);

    let cars = [];

    fetch(`http://localhost:8080/api/ride_on/user/${username}`, {
      headers: {
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else if (response.status === 403) {
          // setMessages([
          //   ...messages,
          //   {
          //     id: makeId(),
          //     type: "failure",
          //     text: "Car could not be registered.",
          //   },
          // ]);
        } else {
          // setMessages([
          //     ...messages,
          //     {
          //       id: makeId(),
          //       type: "failure",
          //       text: "Unexpected error occured.",
          //     },
          //   ]);
        }
      })
      .then((data) => {
        console.log(data);
        cars = data.cars;

        const roles = authoritiesString.split(",");

        const user = {
          username,
          userId,
          roles,
          token,
          cars,
          hasRole(role) {
            return this.roles.includes(role);
          },
        };

        setAuth((auth) => {
          const updatedAuth = {
            ...auth,
            currentUser: user,
          };

          console.log("before login:", auth.currentUser?.cars);
          console.log("after login:", updatedAuth.currentUser.cars);

          return updatedAuth;
        });

        console.log("after set auth:", auth.currentUser?.cars);

        return user;
      })
      .catch((error) => console.log(error));
  }

  function logout() {
    setAuth({
      ...auth,
      currentUser: null,
    });
    localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
  }

  const changeBackground = () => {
    const rootElement = document.getElementById("root");
    rootElement.style.background = `url(${
      process.env.PUBLIC_URL + "/images/jeep.gif"
    }) repeat-y center fixed`;
    rootElement.style.backgroundSize = "cover";
    rootElement.style.height = "100vh";
  };

  useEffect(() => {
    changeBackground();

    const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);

    if (token) {
      login(token);
    }
  }, []);

  const isPasswordComplex = (password) => {
    let digits = 0;
    let letters = 0;
    let others = 0;

    const characters = [...password];

    for (let c of characters) {
      const charCode = c.charCodeAt(0);

      if (charCode >= 48 && charCode <= 57) {
        // numbers 0-9
        digits++;
      } else if (
        (charCode >= 65 && charCode <= 90) ||
        (charCode >= 97 && charCode <= 122)
      ) {
        // lowercase and uppercase letters
        letters++;
      } else {
        others++;
      }
    }

    return digits > 0 && letters > 0 && others > 0;
  };

  const makeId = () => {
    let id = "";
    let characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (var i = 0; i < 12; i++) {
      id += characters.charAt(Math.floor(Math.random() * 36));
    }
    return id;
  };

  const addCarToCurrentUser = (data) => {
    setAuth((auth) => {
      const currentUser = auth.currentUser;
      const newCurrentUser = {
        ...currentUser,
        cars: [...currentUser.cars, data],
      };

      return { ...auth, currentUser: newCurrentUser };
    });
  };

  return (
    <AuthContext.Provider value={auth}>
      <Nav />
      <div className="m-5">
        <br></br>
      </div>
      <div className="container-fluid text-color">
        <Routes>
          <Route
            path="/signin"
            element={
              auth.currentUser ? (
                <Navigate to="/transport" />
              ) : (
                <Signin
                  messages={messages}
                  setMessages={setMessages}
                  makeId={makeId}
                  isPasswordComplex={isPasswordComplex}
                />
              )
            }
          />
          <Route
            path="/signup"
            element={
              auth.currentUser ? (
                <Navigate to="/transport" />
              ) : (
                <Signup
                  messages={messages}
                  setMessages={setMessages}
                  makeId={makeId}
                  isPasswordComplex={isPasswordComplex}
                />
              )
            }
          />

          <Route path="/about" element={<About />} />

          <Route
            path="/transport"
            element={
              <Transport
                currentUser={auth.currentUser}
                cars={cars}
                setCars={setCars}
              />
            }
          />

          <Route path="/" element={<Home />} />

          <Route
            path="/api/ride_on/trip/form"
            element={
              <TripForm
                messages={messages}
                setMessages={setMessages}
                makeId={makeId}
              />
            }
          />

          <Route
            path="/api/ride_on/trip"
            element={
              <TripFactory
                currentUser={auth.currentUser}
                messages={messages}
                setMessages={setMessages}
                makeId={makeId}
              />
            }
          />

          <Route
            path="/api/ride_on/car/form"
            element={
              <CarForm
                messages={messages}
                setMessages={setMessages}
                addCarToCurrentUser={addCarToCurrentUser}
                makeId={makeId}
              />
            }
          />

          {/* // <Route path="*" element={<NotFound />}/>  */}
        </Routes>
        <MessageFactory messages={messages} setMessages={setMessages} />
      </div>
    </AuthContext.Provider>
  );
}

export default App;
