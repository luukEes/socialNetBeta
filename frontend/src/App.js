import React, { Component } from 'react';
import './App.css';
import Login from './Login';

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
    window.location.href = './dashboard.html';
  };

  render() {
    return (
      <div>
        <Login onLoginSuccess={this.handleLoginSuccess} />
      </div>
    );
  }
}

export default App;