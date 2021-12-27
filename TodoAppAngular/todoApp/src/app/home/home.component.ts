import { ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';
import { TokenStorageService } from '../_service/token-storage.service';
import {  LOCALE_ID } from '@angular/core';

import { DomSanitizer } from '@angular/platform-browser';
import { UrlHelper } from '../_helpers/UrlHelper';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Todo } from '../model/Todo';
import { TodoService } from '../_service/todo.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: []})
export class HomeComponent implements OnInit {

  isLoggedIn = false;
  isLoginFailed = false;

  today?: string;
  todos: Todo[] = [];
  todo: any;
  editedTodo: Todo = null;
  error!: boolean;

  @ViewChild('newtodo', { static: true })
  newtodo!: ElementRef;

  userData: any;

  constructor(public datepipe: DatePipe,private todoService: TodoService,private _sanitizer: DomSanitizer, private activatedRoute: ActivatedRoute, private tokenStorage: TokenStorageService, private elementRef: ElementRef) {
    
   

   }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.userData = JSON.parse(this.tokenStorage.getUser()); 
      this.refrehTodos();
      this.today = this.datepipe.transform(new Date(), 'EEEE, d MMMM yyyy','tr-TR');
    }
    if (!this.isLoggedIn){
        this.redirectLogin();
      }

  }

  private refrehTodos() {
    this.todoService.getTodos(this.userData.id).subscribe({
      next:(data) => {
        this.todos = data;

      }

    });
  }

  redirectLogin(): void {
    window.location.href = '/login';
  }
  add(newtodo: string) {
    if (newtodo != null && newtodo != '') {
        if (this.editedTodo ==null && !this.todos.some(el => el.description === newtodo)) {


            var todoObject:Todo = {userId:this.userData.id,description:newtodo,completed:false};
            this.todoService.addTodo(todoObject).subscribe({
              next:()=>{
                this.newtodo.nativeElement.value = '';
                this.error = false;

            },
              complete:()=>{
                setTimeout(()=>this.refrehTodos(),500);
              },
              error:()=>{
                this.error = true;
              },
              
            });
            
        }else if(this.editedTodo!=null )
        {
          this.editedTodo.description=newtodo;
          this.todoService.editTodo(this.editedTodo).subscribe({
              next:()=>{
                this.newtodo.nativeElement.value = '';
                this.error = false;
            },
              complete:()=>{
                this.editedTodo = null;
                this.refrehTodos();
              },
              error:()=>{
                this.error = true;
              },
              
            });

        } else {
          this.error = true;

        }
    }
}
editTodo(todo: Todo) {
  this.editedTodo=todo;
  this.todo=todo.description;
}
removeTodo(todo: Todo) {
  this.todoService.deleteTodo(todo).subscribe({
    next:()=>{},
    complete:()=>{
        this.refrehTodos();
    },
    error:()=>{
      this.error = true;
    },
  });
}
remove() {
    this.newtodo.nativeElement.value = '';
    this.todo = null;
    this.error = false;
}

done(todo: Todo) {
    this.todoService.setDone(todo).subscribe({
      next:()=>{},
      complete:()=>{},
      error:()=>{},
    });
}

undone(todo: Todo) {
    this.todoService.setUndone(todo).subscribe({
      next:()=>{},
      complete:()=>{},
      error:()=>{},
    });
}
}



