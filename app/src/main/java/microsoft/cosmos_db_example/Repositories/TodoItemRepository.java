package microsoft.cosmos_db_example.Repositories;

/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */


import com.microsoft.azure.spring.data.documentdb.repository.DocumentDbRepository;

import org.springframework.stereotype.Repository;

import microsoft.cosmos_db_example.Models.TodoItem;

@Repository
public interface TodoItemRepository extends DocumentDbRepository<TodoItem, String> {
}