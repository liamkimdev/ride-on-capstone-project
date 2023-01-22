import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";

function Register() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [checkedValues, setCheckedValues] = useState([]);

  const { register, handleSubmit } = useForm();

  const navigate = useNavigate();

  const handleCheckboxChange = (e) => {
    if (e.target.checked) {
      setCheckedValues([...checkedValues, e.target.value]);
    } else {
      setCheckedValues(checkedValues.filter((val) => val !== e.target.value));
    }
  };


  const onSubmit = (userData) => {
    fetch("http://localhost:8080/api/ride_on/user", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userData),
    })
      .then((response) => {
        if (response.status === 201) {
          navigate("/login");
        } else if (response.status === 400) {
          console.log("Error sending...");
        } else {
          console.log("Weird error!");
        }
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
      <h2>Register</h2>

      <form classname="mb-5 form" onSubmit={handleSubmit(onSubmit)}>
        <div>
          <label
            className="form-label"
            type="text"
            id="username"
            htmlFor="user-username"
          >
            Email
          </label>
          <input
            className="form-control"
            type="text"
            id="user-username"
            placeholder="example@example.com"
            onChange={(event) => setEmail(event.target.value)}
            {...register("username", { required: "Must input a username" })}
          />
        </div>

        <div>
          <label
            className="form-label"
            type="password"
            id="password"
            htmlFor="user-password"
          >
            Password
          </label>
          <input
            className="form-control"
            type="password"
            id="user-password"
            onChange={(event) => setPassword(event.target.value)}
            {...register("password", { required: "Must input a password" })}
          />
        </div>

        <div>
          <label
            className="form-label"
            type="text"
            id="firstName"
            htmlFor="user-first-name"
          >
            First Name
          </label>
          <input
            className="form-control"
            type="text"
            id="user-first-name"
            {...register("firstName", {
              required: "Must provide a first name",
            })}
          />
        </div>

        <div>
          <label
            className="form-label"
            type="text"
            id="lastName"
            htmlFor="user-last-name"
          >
            Last Name
          </label>
          <input
            className="form-control"
            type="text"
            id="user-last-name"
            {...register("lastName", { required: "Must define a last name" })}
          />
        </div>

        <div>
          <label
            className="form-label"
            type="text"
            id="bankingAccount"
            htmlFor="user-banking-account"
          >
            Banking Account
          </label>
          <input
            className="form-control"
            type="text"
            id="user-banking-account"
            placeholder="1234567890"
            {...register("bankingAccount", {
              required: "Must provide a banking account",
            })}
          />
        </div>

        <div>
          <label
            className="form-label"
            type="text"
            id="identification"
            htmlFor="user-identification"
          >
            Identification (Driver License, Passport, etc.)
          </label>
          <input
            className="form-control"
            type="text"
            id="user-identification"
            placeholder="1234567890"
            {...register("identification", {
              required: "Must define a identification",
            })}
          />
        </div>

        <label
          className="form-label"
          type="text"
          id="preferences"
          htmlFor="user-preferences"
        >
          Preferences
        </label>
        <div className="">
          <input type="checkbox" value="true" name="insurance" /> I am a
          friendly and talkative person 💛
          <br />
          <input type="checkbox" value="true" name="registration" /> I don't
          mind pet(s) 🐶
          <br />
          <input type="checkbox" value="true" name="registration" /> I'm fine
          with smoking 😗💨
          <br />
          <input type="checkbox" value="true" name="registration" /> I love
          music 🎵
          <p {...register("preferences")}>
            Checked values: {checkedValues.join(", ")}
          </p>
        </div>

        <div className="row offset-4">
          <button className="btn btn-primary mt-3 col-3" type="submit">
            Register
          </button>

          <button
            className="btn btn-secondary mt-3 ms-2 col-3"
            type="button"
            onClick={() => navigate("/home")}
          >
            Cancel
          </button>
        </div>
      </form>
    </>
  );
}

export default Register;
