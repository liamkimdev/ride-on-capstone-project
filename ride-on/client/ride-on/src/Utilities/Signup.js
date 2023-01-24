import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { Form, FormGroup, FormLabel, FormCheck } from "react-bootstrap";

function Signup() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [checkedValues, setCheckedValues] = useState([]);

  const { register,
    handleSubmit,
    formState: { errors } } = useForm();

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
          alert("Account Successfully Created")
          navigate("/signin");
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
      <div className="text-center">
        <h3>Sign Up</h3>
      </div>

      <form classname="mb-5 form" onSubmit={handleSubmit(onSubmit)}>
        <div>
          <label
            className="form-label"
            type="text"
            id="username"
            htmlFor="user-username"
          >

          </label>
          <input
            className="form-control"
            type="text"
            id="user-username"
            placeholder="Email"
            onChange={(event) => setEmail(event.target.value)}
            {...register("username", { required: "Must input a username" })}
          />
          <p className="form-errors">
            {errors.username?.message}
          </p>
        </div>

        <div>
          <label
            className="form-label"
            type="password"
            id="password"
            htmlFor="user-password"
          >
          </label>
          <input
            className="form-control"
            type="password"
            id="user-password"
            placeholder="Password"
            onChange={(event) => setPassword(event.target.value)}
            {...register("password", { required: "Must input a password" })}
          />
          <p className="form-errors">
            {errors.password?.message}
          </p>
        </div>

        <div>
          <label
            className="form-label"
            type="text"
            id="firstName"
            htmlFor="user-first-name"
          >
          </label>
          <input
            className="form-control"
            type="text"
            id="user-first-name"
            placeholder="First Name"
            {...register("firstName", {
              required: "Must provide a first name",
            })}
          />
          <p className="form-errors">
            {errors.firstName?.message}
          </p>
        </div>

        <div>
          <label
            className="form-label"
            type="text"
            id="lastName"
            htmlFor="user-last-name"
          >
          </label>
          <input
            className="form-control"
            type="text"
            id="user-last-name"
            placeholder="Last Name"
            {...register("lastName", { required: "Must define a last name" })}
          />
          <p className="form-errors">
            {errors.lastName?.message}
          </p>
        </div>

        <div>
          <label
            className="form-label"
            type="text"
            id="bankingAccount"
            htmlFor="user-banking-account"
          >
          </label>
          <input
            className="form-control"
            type="text"
            id="user-banking-account"
            placeholder="Banking Account No."
            {...register("bankingAccount", {
              required: "Must provide a banking account",
            })}
          />
          <p className="form-errors">
            {errors.bankingAccount?.message}
          </p>
        </div>

        <div>
          <label
            className="form-label"
            type="text"
            id="identification"
            htmlFor="user-identification"
          >
          </label>
          <input
            className="form-control"
            type="text"
            id="user-identification"
            placeholder="Identification No."
            {...register("identification", {
              required: "Must define a identification",
            })}
          />
          <p className="form-errors">
            {errors.identification?.message}
          </p>
        </div>

        <Form>
          <FormGroup>
            <FormLabel><strong>Preferences</strong></FormLabel>
            <div>
              <FormCheck
                type="checkbox"
                label="I am a friendly and talkative person 💛"
                name="insurance"
                value="true"
                id="insurance"
              />
              <FormCheck
                type="checkbox"
                label="I don't mind pet(s) 🐶"
                name="registration"
                value="true"
                id="registration"
              />
              <FormCheck
                type="checkbox"
                label="I'm fine with smoking 😗💨"
                name="registration"
                value="true"
                id="registration"
              />
              <FormCheck
                type="checkbox"
                label="I love music 🎵"
                name="registration"
                value="true"
                id="registration"
              />
            </div>
          </FormGroup>
        </Form>


        <div className="text-center">
          <button className="btn btn-primary mt-3 col-3" type="submit">
            Sign Up
          </button>

          <button
            className="btn btn-secondary mt-3 ms-2 col-3"
            type="button"
            onClick={() => navigate("/transport")}
          >
            Cancel
          </button>

          <div className="m-4">
            <p>Already a member? <a href="#!" onClick={() => navigate("/signin")}>Sign In</a></p>
          </div>
        </div>
      </form>
    </>
  );
}

export default Signup;
