import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

let stompClient = null;

export const connectWebSocket = (onMessageReceived) => {
    const socket = new SockJS('http://localhost:8080/ws-notificaciones');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/notificaciones', (message) => {
            onMessageReceived(JSON.parse(message.body));
        });
    });
};

export const disconnectWebSocket = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
};