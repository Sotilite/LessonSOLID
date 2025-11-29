package ru.urfu.commands;

/**
 * Команда выполнения операции
 */
public interface Command {
    /**
     * Выполнить команду
     */
    void execute();

    /**
     * Получить название команды
     */
    String getCommand();
}
