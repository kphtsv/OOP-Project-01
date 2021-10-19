package ru.betry;

enum BotType {
    Telegram,
    YandexAlice
}

enum CoreMessageType {
    help,
    unknown,
    simple,
    notSupportedType,
    restart
}

public class ParserToBotCore {

    private BotCore core;

    public ParserToBotCore(){
        core = new BotCore();
    }

    public String[] getAnswerForUser(ChatInfoClass chatInfo) {
        CoreMessageType messageType = parseMessageType(chatInfo.getUserMessage(), chatInfo.getBotType());

        switch (messageType) {
            case help:
                return core.getHelp();
            case unknown:
                return core.getUnknownCommand();
            case simple:
                return core.getSimpleMessage(chatInfo);
            case restart:
                return core.getRestart(chatInfo);
            default:
                return core.getNotSupportedType();
        }
    }

    private CoreMessageType parseMessageType(String userMessage, BotType type) {
        switch (type) {
            case Telegram: {
                if (userMessage.startsWith("/"))
                    return getTelegramCommand(userMessage);
                else
                    return CoreMessageType.simple;
            }
            default:
                return CoreMessageType.notSupportedType;
        }
    }

    private CoreMessageType getTelegramCommand(String userCommand){
        switch (userCommand) {
            case ("/help"):
                return CoreMessageType.help;
            case ("/restart"):
                return CoreMessageType.restart;
            default:
                return CoreMessageType.unknown;
        }
    }
}
