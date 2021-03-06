import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { EditorComponent } from './editor/editor.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { VocabPredictorAudiencesService } from './vocab-predictor-audiences.service';
import { VocabPredictorRetrieverService } from './vocab-predictor-retriever.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    AppComponent,
    EditorComponent,
    SidebarComponent
  ],
  imports: [
    BrowserModule, FormsModule, HttpClientModule, NgbModule.forRoot()
  ],
  providers: [
    VocabPredictorAudiencesService,
    VocabPredictorRetrieverService],
  bootstrap: [AppComponent]
})
export class AppModule { }
