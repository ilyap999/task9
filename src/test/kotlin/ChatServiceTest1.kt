import org.junit.Test

import org.junit.Assert.*

class ChatServiceTest {

    @Test
    fun getUnreadChatsCount() {
        val service = ChatService()
        service.addMessage(1, 2, "Привет! Как дела?")
        service.addMessage(2, 1, "Привет! Норм, сам как?")
        service.addMessage(3, 2, "Hello.")
        service.addMessage(3, 2, "Hello. Hello!")
        val countUnreadChat = service.getUnreadChatsCount(2)
        assertEquals(2, countUnreadChat)
    }

    @Test
    fun getListOfMessages() {
        val service = ChatService()
        service.addMessage(1, 2, "Привет! Как дела?")
        service.addMessage(2, 1, "Привет! Норм, сам как?")
        service.addMessage(3, 2, "Hello.")
        service.addMessage(3, 2, "Hello. Hello!")
        val listOfMessage = service.getListOfMessages(2)
        assertEquals(2, listOfMessage.size)
    }
}