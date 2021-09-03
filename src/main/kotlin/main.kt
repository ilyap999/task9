import kotlin.math.max
import kotlin.math.min

fun main() {
    val service = ChatService()
    service.addMessage(1, 2, "Привет! Как дела?")
    service.addMessage(2, 1, "Привет! Норм, сам как?")
    service.addMessage(3, 2, "Hello.")
    service.addMessage(3, 2, "Hello. Hello!")
    val countUnreadChat = service.getUnreadChatsCount(2)
    val listOfMessage = service.getListOfMessages(2)
    //service.deleteMessage(1)
    //service.deleteMessage(3)

}

data class Chat(
    val id: Int,
    val minUserId: Int,
    val maxUserId: Int
)

data class Message(
    val id: Int,
    val idUserSender: Int,
    val idUserReceiver: Int,
    val text: String,
    val idChat: Int,
    var isRead: Boolean
)

class ChatService {
    private var messages = mutableListOf<Message>()
    private var chats = mutableListOf<Chat>()
    private var idMessages = 1
    private var idChats = 1

    // Получить инф. о количестве непрочитанных чатов
    fun getUnreadChatsCount(idUser: Int): Int {
        val listOfUnreadMessages = messages.filter { message: Message ->
            message.idUserReceiver == idUser
        }
        listOfUnreadMessages.sortedBy { (Message) -> idChats }
        var previousId = 0
        var countUnreadChat = 0
        for ((index, messageInList) in listOfUnreadMessages.withIndex()) {
            if (previousId != messageInList.idChat) {
                countUnreadChat ++
            }
            previousId = messageInList.idChat
        }
        return countUnreadChat
    }

    // Получить список сообщений из чата (все отданные сообщения считаются прочитанными)
    fun getListOfMessages(idChat: Int): List<Message> {
        val messagesByIdChat = findMessagesByIdChat(idChat)
        for ((index, messageInList) in messagesByIdChat.withIndex()) {
            setMessageAtRead(messageInList)
            }
        return messagesByIdChat
    }

    fun setMessageAtRead(message: Message) {
        message.isRead = true
    }

    fun addChat(minUserId: Int, maxUserId: Int) {
        chats += Chat(idChats, minUserId, maxUserId)
        idChats ++
    }

    fun findChat(minUserId: Int, maxUserId: Int): Int {
        for ((index, chatInList) in chats.withIndex()) {
            if (minUserId == chatInList.minUserId && maxUserId == chatInList.maxUserId) {
                return chatInList.id
            }
        }
        return 0
    }

    fun findChatById(idChat: Int): Chat {
        val listById = chats.filter { chat: Chat ->
            chat.id == idChat
        }
        return listById.last()
    }

    fun findMessagesByIdChat(idChat: Int): List<Message> {
        val listById = messages.filter { message: Message ->
            message.idChat == idChat
        }
        return listById
    }

    fun addMessage(idUserSender: Int, idUserReceiver: Int, text: String) {
        // ищем уже существующий чат для этого сообщения, если не находим - создаем новый чат
        val minUserId = min(idUserSender, idUserReceiver)
        val maxUserId = max(idUserSender, idUserReceiver)
        val idChat = findChat(minUserId, maxUserId)
        if (idChat == 0) {
            addChat(minUserId, maxUserId)
            messages += Message(idMessages, idUserSender, idUserReceiver, text, idChats - 1, false)
            idMessages ++
        }
        else {
            messages += Message(idMessages, idUserSender, idUserReceiver, text, idChat, false)
            idMessages ++
        }
    }

    fun deleteMessage(idMessage: Int) {
        for ((index, messageInList) in messages.withIndex()) {
            if (idMessage == messageInList.id) {
                val idRemoveChat = messageInList.idChat
                messages.removeAt(index)
                // Если было последнее сообщение в чате - удалить чат
                if (findMessagesByIdChat(idRemoveChat).size == 0) {
                    deleteChatById(idRemoveChat)
                }
                break
            }
        }
    }

    fun deleteChatById(id: Int) {
        for ((index, chatInList) in chats.withIndex()) {
            if (id == chatInList.id) {
                chats.removeAt(index)
                break
            }
        }
    }


}