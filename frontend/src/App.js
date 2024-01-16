import React, { Component } from 'react';
import './App.css';
import Login from './Login';
import HomePage from './HomePage'; // Assume you have a component for the home page

class App extends Component {
  constructor() {
    super();
    this.state = {
      isLoggedIn: false,
    };
  }

  handleLoginSuccess = () => {
    // Assuming you have some logic to determine successful login
    // For demonstration purposes, I'm just setting the state to true
    this.setState({ isLoggedIn: true });
    window.location.href = 'http://192.168.1.23:8081/dashboard';
  };

  render() {
    return (
      <div>
        {this.state.isLoggedIn ? (
          // If logged in, open a new window or navigate to another page
          // You should replace this with the actual logic to open a new window or navigate
          <HomePage />
        ) : (
          // If not logged in, render the Login component
          <Login onLoginSuccess={this.handleLoginSuccess} />
        )}
      </div>
    );
  }
}

export default App;
