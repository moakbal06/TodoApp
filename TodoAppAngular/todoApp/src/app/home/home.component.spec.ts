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
      date: new Date('2024-10-05T14:30:00')
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
      date: null as any
    };

    component.todos = [todoWithoutDate];
    fixture.detectChanges();

    const dateElement: DebugElement = fixture.debugElement.query(By.css('[data-testid="todo-date"]'));
    expect(dateElement).toBeFalsy();
  });

  it('should format date with DatePipe using tr-TR locale', () => {
    const datePipe = TestBed.inject(DatePipe);
    const testDate = new Date('2024-10-05T14:30:45');

    const formatted = datePipe.transform(testDate, 'dd.MM.yyyy HH:mm:ss', undefined, 'tr-TR');

    expect(formatted).toBe('05.10.2024 14:30:45');
  });
});
