import React, { Component } from 'react';
import './App.css';
import Login from './Login';
import Register from './Register'; // Import the Register component

class App extends Component {
  constructor() {
    super();
    this.state = {
      isRegistering: false, // New state to track whether to render Login or Register
    };
  }

  handleLoginSuccess = () => {
    this.setState({ isLoggedIn: true });
    window.location.href = './dashboard.html';
  };

  // Callback to switch to the Register component
  handleSwitchToRegister = () => {
    this.setState({ isRegistering: true });
  };

  render() {
    return (
      <div>
        {this.state.isRegistering ? (
          // If isRegistering is true, render the Register component
          <Register onSwitchToLogin={() => this.setState({ isRegistering: false })} />
        ) : (
          // If isRegistering is false, render the Login component
          <Login onLoginSuccess={this.handleLoginSuccess} onSwitchToRegister={this.handleSwitchToRegister} />
        )}
      </div>
    );
  }
}

export default App;
