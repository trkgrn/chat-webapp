importScripts('https://www.gstatic.com/firebasejs/8.0.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.0.0/firebase-messaging.js');

firebase.initializeApp({
  apiKey: "AIzaSyAgTLB776DTU_LtEcTQSk6ruxIe755SnkM",
  authDomain: "chat-webapp-443b1.firebaseapp.com",
  projectId: "chat-webapp-443b1",
  storageBucket: "chat-webapp-443b1.appspot.com",
  messagingSenderId: "910405942223",
  appId: "1:910405942223:web:692d60bedeb44b8475b0ba",
  measurementId: "G-13WXTVS5J7"
});

const messaging = firebase.messaging();
