import React, { Component } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import RegistrationAlert from './RegistrationAlert.js';
import './Login.css';

class Register extends Component {
  constructor(props) {
    super(props);
    this.registrationAlert = React.createRef();
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const username = event.target.username.value;
    const password = event.target.password.value;

    // Validate password using regex
    const passwordRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.{10,})/;
    if (!passwordRegex.test(password)) {
      this.showRegistrationAlert(
        "danger",
        "Invalid Password",
        "Password must contain at least one capital letter, one special character, and be at least 10 characters long."
      );
      return;
    }

    // Continue with the registration logic
    this.registerUser(username, password);
  };

  registerUser(username, password) {
    fetch('http://localhost:8080/addusers', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        username: username,
        password: password,
      })
    }).then(function(response) {
      if (response.status === 200) {
        this.showRegistrationAlert("success", "User registered!", "You can now log in.");
      } else if (response.status === 422) {
        this.showRegistrationAlert("danger", "User already exists", "Please choose a different name.");
      } else {
        this.showRegistrationAlert("danger", "User not registered!", "Something went wrong.");
      }
    }.bind(this)).catch(function(error) {
      this.showRegistrationAlert("danger", "Error", "Something went wrong.");
    }.bind(this));

    // For demonstration purposes, assuming successful registration
    this.showRegistrationAlert('success', 'User registered!', 'You can now log in.');
  }

  showRegistrationAlert(variant, heading, message) {
    this.registrationAlert.current.setVariant(variant);
    this.registrationAlert.current.setHeading(heading);
    this.registrationAlert.current.setMessage(message);
    this.registrationAlert.current.setVisible(true);
  }

  render() {
    return (
      <>
        <div className="Login">
          <h1 className="RegisterHeader">Register</h1>
          <Form onSubmit={this.handleSubmit}>
            <Form.Group controlId="username" size="lg">
              <Form.Label>Username</Form.Label>
              <Form.Control autoFocus name="username" />
            </Form.Group>

            <Form.Group controlId="password" size="lg">
              <Form.Label>Password</Form.Label>
              <Form.Control type="password" name="password" />
            </Form.Group>

            <Button block size="lg" type="submit">
              Register
            </Button>
          </Form>
        </div>

        <div className="LoginButtonContainer">
          <p className="already-have-account">Already have an account?</p>
          <Button block size="lg" onClick={this.props.onSwitchToLogin}>
            Login
          </Button>
        </div>

        <RegistrationAlert ref={this.registrationAlert} />
      </>
    );
  }
}

export default Register;
