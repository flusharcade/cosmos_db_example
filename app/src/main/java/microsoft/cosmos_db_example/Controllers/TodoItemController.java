package microsoft.cosmos_db_example.Controllers;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import microsoft.cosmos_db_example.Models.TodoItem;
import microsoft.cosmos_db_example.Services.CosmosDBService;

/**
 * Created by mww121 on 26/10/17.
 */

public class TodoItemController {
    public static TodoItemController getInstance() {
        if (todoItemController == null) {
            todoItemController = new TodoItemController(new CosmosDBService());
        }
        return todoItemController;
    }

    private static TodoItemController todoItemController;

    private final CosmosDBService cosmosDBService;

    TodoItemController(CosmosDBService cosmosDBService) {
        this.cosmosDBService = cosmosDBService;
    }

    public TodoItem createTodoItem(@NonNull String name,
                                   @NonNull String category, boolean isComplete) {
        TodoItem todoItem = new TodoItem();
        return cosmosDBService.createTodoItem(todoItem);
    }

    public boolean deleteTodoItem(@NonNull String id) {
        return cosmosDBService.deleteTodoItem(id);
    }

    public TodoItem getTodoItemById(@NonNull String id) {
        return cosmosDBService.readTodoItem(id);
    }

    public ArrayList<TodoItem> getTodoItems() {
        return cosmosDBService.readTodoItems();
    }

    public TodoItem updateTodoItem(@NonNull String id, boolean isComplete) {
        return cosmosDBService.updateTodoItem(id, isComplete);
    }
}