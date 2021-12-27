import { Injectable } from '@angular/core';
import { Todo } from '../model/Todo';
import { HttpClient,HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { UrlHelper } from '../_helpers/UrlHelper';

const apiUrl = UrlHelper.BASE_URL+'/api/todo/';

@Injectable({
  providedIn: 'root'
})
export class TodoService {
 
  constructor( private http : HttpClient) { }

  setUndone(todo: Todo) {
    return this.http.post(apiUrl+`setUndone`,todo); 
   }
  setDone(todo: Todo):Observable<any> {
    return this.http.post(apiUrl+`setDone`,todo);
  }
  removeImportant(todo: Todo):Observable<any> {
    return this.http.put(apiUrl+`removeImportant`,todo);
  }
  addImportant(todo: Todo):Observable<any> {
    return this.http.put(apiUrl+`addImportant`,todo);
  }
  addTodo(newTodo: any):Observable<any> {
    return this.http.post(apiUrl+`addTodo`,newTodo);
  }
  deleteTodo(todo: Todo):Observable<any> {
    return this.http.delete(apiUrl+`deleteTodo/${todo.id}`);
  }
  getTodos(userId:any):Observable<Todo[]> {
    return this.http.get<Todo[]>(apiUrl+`getTodos/${userId}`);
  }
  editTodo(todoObject: Todo):Observable<any> {
    return this.http.put(apiUrl+`editTodo`,todoObject);
  }

}
