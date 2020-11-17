import { Component, OnInit, OnDestroy } from '@angular/core';
import * as Highcharts from 'highcharts/highstock';
import { Subscription } from 'rxjs';
import { SalesStatsService } from 'src/app/service/sales-stats.service';
import * as Constants from '../../../models/constants';

@Component({
  selector: 'app-multi-line-chart',
  templateUrl: './multi-line-chart.component.html',
  styleUrls: ['./multi-line-chart.component.css']
})
export class MultiLineChartComponent implements OnInit, OnDestroy {
  statisticsdata: any[];
  benchmarkdata: any[];
  salesStatsSubscription: Subscription;

  chartObject: any;

  chartOptions: any = {
    rangeSelector: {
      enabled: true
    },
    title: {
      text: Constants.HIGHCHARTS_CONSTS.TITLE
    },
    scrollbar: {
      enabled: true,
    },
    xAxis: [{
      startOnTick: true,
      endOnTick: true,
    }],
    tooltip: {
      split: true,
    },
    series: [{
      tooltip: {
        valueDecimals: 2
      },
      type: Constants.HIGHCHARTS_CONSTS.LINE,
      name: Constants.HIGHCHARTS_CONSTS.SERIES_TITLE,
      pointWidth: 5,
      data: [
      ]
    },
    {
      tooltip: {
        valueDecimals: 2
      },
      dashStyle: Constants.HIGHCHARTS_CONSTS.DASH,
      name: Constants.HIGHCHARTS_CONSTS.BENCHMARK,
      pointWidth: 5,
      data: [
      ]
    }
    ],
    time: {
      useUTC: true
    }
  };

  constructor(private salesSubObservableService: SalesStatsService) { }

  ngOnInit(): void {
        this.salesStatsSubscription = this.salesSubObservableService.getSalesStats().subscribe(
          (data: any) => {
            this.chartOptions.series[0].data = data[0];
            this.chartOptions.series[1].data = data[1];

            this.chartObject = Highcharts.stockChart(Constants.HIGHCHARTS_CONSTS.CHART, this.chartOptions);
            this.chartObject.xAxis[0].setExtremes(
              Date.UTC(2020, 0, 1, 12),
              Date.UTC(2020, 9, 15, 12)
            );
          });
    }

      ngOnDestroy(): void {
        // unsubscribe to ensure no memory leaks
        this.salesStatsSubscription.unsubscribe();
    }
  }
