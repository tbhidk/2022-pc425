
var admin = require("firebase-admin");

// path nya sesuaikan lokasi file require("sesuaikan")
var serviceAccount = require("C:/javascript-projects/firebase/firebase-admin/util/smarthome-a22bf-firebase-adminsdk-fdu58-167d6b023e.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://smarthome-a22bf-default-rtdb.asia-southeast1.firebasedatabase.app"
});

const db = admin.firestore();
module.exports = { admin, db };
