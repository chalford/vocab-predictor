import { Component, OnInit } from '@angular/core';
import { Term } from '../term';
import { VocabPredictorAudiencesService } from '../vocab-predictor-audiences.service';
import { VocabPredictorRetrieverService } from '../vocab-predictor-retriever.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  programmes = [];
  selectedProgramme = '';
  terms = [];
  text = '';

  constructor(
    private audiencesService: VocabPredictorAudiencesService,
    private termsService: VocabPredictorRetrieverService  ) { }

  ngOnInit() {
    this.getAudiences();
    this.getTerms(this.selectedProgramme, this.text);
  }

  onChange(programme) {
    this.selectedProgramme = programme;
    this.getTerms(programme, this.text);
  }

  getAudiences(): void {
    this.audiencesService.getAudiences()
      .subscribe(p => {
        this.programmes = p;
        this.selectedProgramme = this.programmes[1];
      });
  }

  getTerms(programme, text): void {
    this.terms = [];
    this.termsService.getTerms(programme, text)
    .subscribe(t => {
        this.terms = t.terms.slice(0, 15);
    });
  }

  suggest(text): void {
    this.getTerms(this.selectedProgramme, text);
  }

}
