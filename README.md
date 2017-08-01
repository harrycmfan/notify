# notify component is a spring based message send and receive frame

It uses zookeeper as a register center.
It uses activeMQ as message queue.

It is used spring AOP and DI, we can inject our service function as a @Producer
It will automatically send the response as an message to MQ

Consuming service will get the message and process.
