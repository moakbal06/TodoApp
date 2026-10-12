import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import { TodoService } from '../_service/todo.service';
import { TokenStorageService } from '../_service/token-storage.service';
import { MessageService } from 'primeng/api';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { DatePipe, registerLocaleData } from '@angular/common';
import { of } from 'rxjs';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import localeTr from '@angular/common/locales/tr';

import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { PanelModule } from 'primeng/panel';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';

registerLocaleData(localeTr, 'tr-TR');

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let todoServiceSpy: jasmine.SpyObj<TodoService>;
  let tokenStorageServiceSpy: jasmine.SpyObj<TokenStorageService>;

  beforeEach(async () => {
    const todoSpy = jasmine.createSpyObj('TodoService', ['getTodos', 'addTodo', 'deleteTodo', 'setDone', 'setUndone', 'editTodo']);
    const tokenSpy = jasmine.createSpyObj('TokenStorageService', ['getToken', 'getUser']);

    await TestBed.configureTestingModule({
      declarations: [ HomeComponent ],
      imports: [
        HttpClientTestingModule,
        FormsModule,
        RouterTestingModule,
        NoopAnimationsModule,
        ButtonModule,
        TableModule,
        PanelModule,
        CheckboxModule,
        InputTextModule
      ],
      providers: [
        { provide: TodoService, useValue: todoSpy },
        { provide: TokenStorageService, useValue: tokenSpy },
        MessageService,
        DatePipe
      ]
    })
    .compileComponents();

    todoServiceSpy = TestBed.inject(TodoService) as jasmine.SpyObj<TodoService>;
    tokenStorageServiceSpy = TestBed.inject(TokenStorageService) as jasmine.SpyObj<TokenStorageService>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;

    tokenStorageServiceSpy.getUser.and.returnValue(JSON.stringify({ id: 'user1', username: 'testuser' }));
    tokenStorageServiceSpy.getToken.and.returnValue('fake-token');
    todoServiceSpy.getTodos.and.returnValue(of([]));

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display date when todo has date property', () => {
    const todoWithDate = {
      id: '1',
      userId: 'user1',
      description: 'Test Todo',
      completed: false,
      date: new Date('2024-10-05T14:30:00'),
      category: 'Test Category'
    };

    component.todos = [todoWithDate];
    fixture.detectChanges();

    const dateElement: DebugElement = fixture.debugElement.query(By.css('[data-testid="todo-date"]'));
    expect(dateElement).toBeTruthy();
    expect(dateElement.nativeElement.textContent.trim()).toContain('05.10.2024');
  });

  it('should not display date when todo has no date property', () => {
    const todoWithoutDate = {
      id: '2',
      userId: 'user1',
      description: 'Old Todo',
      completed: false,
      date: null as any,
      category: 'Old Category'
    };

    component.todos = [todoWithoutDate];
    fixture.detectChanges();

    const dateElement: DebugElement = fixture.debugElement.query(By.css('[data-testid="todo-date"]'));
    expect(dateElement).toBeFalsy();
  });

  it('should display category when todo has category property', () => {
    const todoWithCategory = {
      id: '3',
      userId: 'user1',
      description: 'Todo with Category',
      completed: false,
      date: new Date('2024-10-05T14:30:00'),
      category: 'Work'
    };

    component.todos = [todoWithCategory];
    fixture.detectChanges();

    const categoryElement: DebugElement = fixture.debugElement.query(By.css('[data-testid="todo-category"]'));
    expect(categoryElement).toBeTruthy();
    expect(categoryElement.nativeElement.textContent.trim()).toContain('Work');
  });

  it('should not display category when todo has no category property', () => {
    const todoWithoutCategory = {
      id: '4',
      userId: 'user1',
      description: 'Todo without Category',
      completed: false,
      date: new Date('2024-10-05T14:30:00'),
      category: null as any
    };

    component.todos = [todoWithoutCategory];
    fixture.detectChanges();

    const categoryElement: DebugElement = fixture.debugElement.query(By.css('[data-testid="todo-category"]'));
    expect(categoryElement).toBeFalsy();
  });

  it('should format date with DatePipe using tr-TR locale', () => {
    const datePipe = TestBed.inject(DatePipe);
    const testDate = new Date('2024-10-05T14:30:45');

    const formatted = datePipe.transform(testDate, 'dd.MM.yyyy HH:mm:ss', undefined, 'tr-TR');

    expect(formatted).toBe('05.10.2024 14:30:45');
  });

  it('should send category when adding new todo', () => {
    todoServiceSpy.addTodo.and.returnValue(of({}));

    component.todo = 'New Task';
    component.todoCategory = 'Work';
    component.add('New Task');

    expect(todoServiceSpy.addTodo).toHaveBeenCalledWith(
      jasmine.objectContaining({
        description: 'New Task',
        category: 'Work',
        userId: 'user1',
        completed: false
      })
    );
  });

  it('should clear category after adding todo', () => {
    todoServiceSpy.addTodo.and.returnValue(of({}));
    todoServiceSpy.getTodos.and.returnValue(of([]));

    component.todo = 'New Task';
    component.todoCategory = 'Work';
    component.add('New Task');

    expect(component.todoCategory).toBe('');
  });

  it('should load category when editing todo', () => {
    const todoToEdit = {
      id: '5',
      userId: 'user1',
      description: 'Edit Me',
      completed: false,
      category: 'Personal'
    };

    component.editTodo(todoToEdit);

    expect(component.todoCategory).toBe('Personal');
    expect(component.todo).toBe('Edit Me');
    expect(component.editedTodo).toBe(todoToEdit);
  });

  it('should update category when editing todo', () => {
    const todoToEdit = {
      id: '6',
      userId: 'user1',
      description: 'Original',
      completed: false,
      category: 'Old Category'
    };

    todoServiceSpy.editTodo.and.returnValue(of({}));
    todoServiceSpy.getTodos.and.returnValue(of([]));

    component.editedTodo = todoToEdit;
    component.todoCategory = 'New Category';
    component.add('Updated Description');

    expect(todoServiceSpy.editTodo).toHaveBeenCalledWith(
      jasmine.objectContaining({
        id: '6',
        description: 'Updated Description',
        category: 'New Category'
      })
    );
  });

  it('should clear category when remove is called', () => {
    component.todoCategory = 'Some Category';
    component.todo = 'Some Todo';

    component.remove();

    expect(component.todoCategory).toBe('');
    expect(component.todo).toBeNull();
  });

  it('should handle add with empty category', () => {
    todoServiceSpy.addTodo.and.returnValue(of({}));

    component.todo = 'Task without category';
    component.todoCategory = '';
    component.add('Task without category');

    expect(todoServiceSpy.addTodo).toHaveBeenCalledWith(
      jasmine.objectContaining({
        description: 'Task without category',
        category: '',
        completed: false
      })
    );
  });
});
