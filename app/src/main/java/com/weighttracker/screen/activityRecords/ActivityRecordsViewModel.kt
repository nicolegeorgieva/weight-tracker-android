package com.weighttracker.screen.activityRecords

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.persistence.database.activityrecords.ActivityRecordsFlow
import com.weighttracker.persistence.database.activityrecords.DeleteActivityRecordAct
import com.weighttracker.persistence.database.activityrecords.WriteActivityRecordAct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class ActivityRecordsViewModel @Inject constructor(
    private val activityRecordsFlow: ActivityRecordsFlow,
    private val writeActivityRecordAct: WriteActivityRecordAct,
    private val deleteActivityRecordAct: DeleteActivityRecordAct
) : SimpleFlowViewModel<ActivityRecordsState, ActivityRecordsEvent>() {
    override val initialUi = ActivityRecordsState(
        //while loading
        activityRecords = emptyList()
    )

    override val uiFlow: Flow<ActivityRecordsState> = combine(
        activityRecordsFlow(Unit),
        activityRecordsFlow(Unit),

        ) { activityRecords, _ ->
        ActivityRecordsState(
            activityRecords = activityRecords.sortedByDescending {
                it.dateTime
            }
        )
    }

    override suspend fun handleEvent(event: ActivityRecordsEvent) {
        when (event) {
            is ActivityRecordsEvent.DeleteActivityRecord -> {
                deleteActivityRecordAct(event.record.id)
            }
            is ActivityRecordsEvent.UpdateActivityRecord -> {
                writeActivityRecordAct(event.newRecord)
            }
            else -> {}
        }
    }
}