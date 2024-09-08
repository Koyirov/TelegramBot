package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

//import static java.lang.Math.toIntExact;

public class MyBot extends TelegramLongPollingBot {

    String previousChoose = null;


    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {

            if(update.getMessage().getText().equals("/start")){

                long chat_id = update.getMessage().getChatId();
                SendMessage message = new SendMessage();
                message.setChatId(chat_id);
                message.setText(MyConsts.WELCOME_TEXT);

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

                List<InlineKeyboardButton> firstRowInline = new ArrayList<>();

                InlineKeyboardButton ownStoryButton = new InlineKeyboardButton();
                ownStoryButton.setText(MyConsts.OWN_STORY_BUTTON_TEXT);
                ownStoryButton.setCallbackData(MyConsts.OWN_STORY_BUTTON_CALLBACK_TEXT);
                firstRowInline.add(ownStoryButton);

                InlineKeyboardButton othersStoryButton = new InlineKeyboardButton();
                othersStoryButton.setText(MyConsts.OTHERS_STORY_BUTTON_TEXT);
                othersStoryButton.setCallbackData(MyConsts.OTHERS_STORY_BUTTON_CALLBACK_TEXT);
                firstRowInline.add(othersStoryButton);
                // Set the keyboard to the markup
                rowsInline.add(firstRowInline);

                List<InlineKeyboardButton> secondRowInline = new ArrayList<>();

                InlineKeyboardButton quoteStoryButton = new InlineKeyboardButton();
                quoteStoryButton.setText(MyConsts.QUOTE_BUTTON_TEXT);
                quoteStoryButton.setCallbackData(MyConsts.QUOTE_BUTTON_CALLBACK_TEXT);
                secondRowInline.add(quoteStoryButton);

                InlineKeyboardButton poemStoryButton = new InlineKeyboardButton();
                poemStoryButton.setText(MyConsts.POEM_BUTTON_TEXT);
                poemStoryButton.setCallbackData(MyConsts.POEM_BUTTON_CALLBACK_TEXT);
                secondRowInline.add(poemStoryButton);
                // Set the keyboard to the markup
                rowsInline.add(secondRowInline);

                markupInline.setKeyboard(rowsInline);
                message.setReplyMarkup(markupInline);

                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else  {
                long chat_id = update.getMessage().getChatId();
                String text = update.getMessage().getText();
                SendMessage message = new SendMessage();
                message.setChatId(chat_id);

                String answerTemplate = null;

                if (previousChoose.equals(MyConsts.OWN_STORY_BUTTON_CALLBACK_TEXT)) {

                    answerTemplate = MyConsts.POST_OWN_STORY_TEXT + text + MyConsts.HEART_COUNCIL_SIGN;
                    previousChoose = null;

                } else if (previousChoose.equals(MyConsts.OTHERS_STORY_BUTTON_CALLBACK_TEXT)) {

                    answerTemplate = MyConsts.POST_OTHERS_STORY_TEXT + text + MyConsts.HEART_COUNCIL_SIGN;
                    previousChoose = null;

                } else if (previousChoose.equals(MyConsts.QUOTE_BUTTON_CALLBACK_TEXT)) {

                    answerTemplate = MyConsts.POST_QUOTE_TEXT + text + MyConsts.HEART_COUNCIL_SIGN;
                    previousChoose = null;

                } else if (previousChoose.equals(MyConsts.POEM_BUTTON_CALLBACK_TEXT)) {
                    answerTemplate = MyConsts.POST_POEM_TEXT + text + MyConsts.HEART_COUNCIL_SIGN;
                    previousChoose = null;

                }

                message.setText(answerTemplate);

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> firstRowInline = new ArrayList<>();

                InlineKeyboardButton submitStoryButton = new InlineKeyboardButton();
                submitStoryButton.setText(MyConsts.SUBMIT_BUTTON_TEXT);
                submitStoryButton.setCallbackData(MyConsts.SUBMIT_BUTTON_CALLBACK_TEXT);
                firstRowInline.add(submitStoryButton);

                InlineKeyboardButton cancelStoryButton = new InlineKeyboardButton();
                cancelStoryButton.setText(MyConsts.CANCEL_BUTTON_TEXT);
                cancelStoryButton.setCallbackData(MyConsts.CANCEL_BUTTON_CALLBACK_TEXT);
                firstRowInline.add(cancelStoryButton);

                rowsInline.add(firstRowInline);
                markupInline.setKeyboard(rowsInline);
                message.setReplyMarkup(markupInline);

                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }



        //Button click
        } else if (update.hasCallbackQuery()) {
            // Set variables
            String call_data = update.getCallbackQuery().getData();
//            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();

            if(call_data.equals(MyConsts.SUBMIT_BUTTON_CALLBACK_TEXT)){
                SendMessage new_message = new SendMessage();
                new_message.setChatId(chat_id);
                new_message.setText(MyConsts.SUBMIT_ANSWER_TEXT);

                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else if(call_data.equals(MyConsts.CANCEL_BUTTON_CALLBACK_TEXT)){
                SendMessage new_message = new SendMessage();
                new_message.setChatId(chat_id);
                new_message.setText(MyConsts.CANCEL_ANSWER_TEXT);

                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (call_data.equals(MyConsts.OWN_STORY_BUTTON_CALLBACK_TEXT)) {

                previousChoose = MyConsts.OWN_STORY_BUTTON_CALLBACK_TEXT;

                String answer = MyConsts.WRITE_OWN_STORY_TEXT;
                SendMessage new_message = new SendMessage();
                new_message.setChatId(chat_id);
//                new_message.setMessageId(toIntExact(message_id));
                new_message.setText(answer);

                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (call_data.equals(MyConsts.OTHERS_STORY_BUTTON_CALLBACK_TEXT)) {

                previousChoose = MyConsts.OTHERS_STORY_BUTTON_CALLBACK_TEXT;

                String answer = MyConsts.WRITE_OTHERS_STORY_TEXT;
                SendMessage new_message = new SendMessage();
                new_message.setChatId(chat_id);
//                new_message.setMessageId(toIntExact(message_id));
                new_message.setText(answer);

                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (call_data.equals(MyConsts.QUOTE_BUTTON_CALLBACK_TEXT)) {

                previousChoose = MyConsts.QUOTE_BUTTON_CALLBACK_TEXT;

                String answer = MyConsts.WRITE_QUOTE_TEXT;
                SendMessage new_message = new SendMessage();
                new_message.setChatId(chat_id);
//                new_message.setMessageId(toIntExact(message_id));
                new_message.setText(answer);

                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (call_data.equals(MyConsts.POEM_BUTTON_CALLBACK_TEXT)) {

                previousChoose = MyConsts.POEM_BUTTON_CALLBACK_TEXT;

                String answer = MyConsts.WRITE_POEM_TEXT;
                SendMessage new_message = new SendMessage();
                new_message.setChatId(chat_id);
//                new_message.setMessageId(toIntExact(message_id));
                new_message.setText(answer);

                try {
                    execute(new_message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "Here comes bot name";
    }

    @Override
    public String getBotToken(){
        return "Here comes bot token";
    }
}
