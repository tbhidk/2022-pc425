const firebase = require('firebase');

// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyDQWMSvugjpUg2yqAju69MfXC2dm9Zax8w",
  authDomain: "smarthome-a22bf.firebaseapp.com",
  databaseURL: "https://smarthome-a22bf-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "smarthome-a22bf",
  storageBucket: "smarthome-a22bf.appspot.com",
  messagingSenderId: "814051016817",
  appId: "1:814051016817:web:38b96034e8ead75ac55bd0",
  measurementId: "G-47ZVSB8G3D"
};

firebase.initializeApp(firebaseConfig); //initialize firebase app 
module.exports = { firebase }; //export the app