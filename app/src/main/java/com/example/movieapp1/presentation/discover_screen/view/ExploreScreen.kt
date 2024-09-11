import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.movieapp1.presentation.discover_screen.Question
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.OutlinedButton
import com.example.movieapp1.presentation.discover_screen.DiscoverScreenViewModel
import com.example.movieapp1.presentation.discover_screen.FilterOptions
import com.example.movieapp1.presentation.search_screen.MovieSection


@Composable
fun ExploreScreen(
    paddingValues: PaddingValues,
    navController:NavController,
    viewModel: DiscoverScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val questions = listOf(
        Question(
            text = "Aksiyon filmleri mi, dram filmleri mi tercih edersiniz?",
            answer1 = "Aksiyon",
            answer2 = "Dram"
        ),
        Question(
            text = "Macera filmlerini mi, romantik filmleri mi tercih edersiniz?",
            answer1 = "Macera",
            answer2 = "Romantik"
        ),
        Question(
            text = "Güncel filmlerle mi yoksa eski filmlerle mi ilgilenirsiniz??",
            answer1 = "Güncel",
            answer2 = "Eski"
        ),
        Question(
            text = "Filmlerin sıralamasını popülerliğe göre mi, puanlarına göre mi yapmak istersiniz?",
            answer1 = "Popülerlik",
            answer2 = "Puan"
        ),
        Question(
            text = "Sıralamayı artan olarak mı, azalan olarak mı şekillendirmek istersiniz?",
            answer1 = "Artan",
            answer2 = "Azalan"
        ),
        Question(
            text = "Yetişkin filmleri engellemek ister misiniz?",
            answer1 = "Evet",
            answer2 = "Hayır"
        ),
        Question(
            text = "Filmler keşfetmeye hazır mısınız?",
            answer1 = "Evet",
            answer2 = "Evet"
        )
    )

    val currentIndex= remember {
     mutableIntStateOf(0)
    }
    val currentQuestion = remember { derivedStateOf { questions[currentIndex.intValue] } }


    val answers = remember { mutableStateListOf<String?>().apply { repeat(questions.size) { add(null) } } }

    fun processAnswers(answers: List<String?>): FilterOptions {
        val filterOptions = FilterOptions()

        answers.forEachIndexed { index, answer ->
            when (index) {
                0 -> { // Aksiyon mu Dram mı?
                    filterOptions.genreIds = if (answer == "Aksiyon") "28" else "18" // Örneğin: 28 -> Aksiyon, 18 -> Dram
                }
                1 -> { // Macera mı Romantik mi?
                    filterOptions.genreIds = if (answer == "Macera") "12" else "10749" // 12 -> Macera, 10749 -> Romantik
                }
                2 -> { // Son 5 yılda çıkan filmlerle mi ilgilenirsiniz?
                    if (answer == "Güncel") filterOptions.releaseDateGte = "2019-01-01" else filterOptions.releaseDateLte = "2018-12-31"
                }
                3 -> { // Popülerliğe göre mi, puanlarına göre mi sıralamak istersiniz?
                    filterOptions.sortBy = if (answer == "Popülerlik") "popularity.desc" else "vote_average.desc"
                }
                4 -> { // Sıralamayı artan mı azalan mı yapmak istersiniz?
                    filterOptions.sortBy = if (answer == "Artan") filterOptions.sortBy?.replace(".desc", ".asc") else filterOptions.sortBy
                }
                5 -> { // Yetişkin filmleri engellemek ister misiniz?
                   filterOptions.includeAdult= answer != "Evet"
                }
            }
        }

        return filterOptions
    }

    fun applyFilters(answers: List<String?>) {
        val filterOptions = processAnswers(answers)
        viewModel.getFilteredMovies(
            sortBy = filterOptions.sortBy,
            genreIds = filterOptions.genreIds,
            minVote = filterOptions.minVote,
            maxVote = filterOptions.maxVote,
            releaseDateGte = filterOptions.releaseDateGte,
            releaseDateLte = filterOptions.releaseDateLte,
            originalLanguage = filterOptions.originalLanguage,
            includeAdult = filterOptions.includeAdult,
            voteCount = 50,//filterOptions.voteCount,
            page = 1 // İlk sayfa için
        )
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        state.movies?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Keşfedilen Filmler")
            Spacer(modifier = Modifier.height(8.dp))
            MovieSection(
                title = "",
                movies = it,
                onClick = {
                    navController.navigate("movie_detail_screen/${it.id}")
                }
            )
        }



        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            if (currentIndex.intValue>0){
                MyButton(text = "Geri") {
                    currentIndex.intValue-=1
                }
            }


        }
        Spacer(modifier = Modifier.height(20.dp))
        SwipeableCard(
            question =currentQuestion.value,
            onSwipeLeft = {
                if (currentIndex.intValue <=questions.size-1) {
                    answers[currentIndex.intValue] = currentQuestion.value.answer1
                    if (currentIndex.intValue == (questions.size - 1)) {
                        answers.toList().let {
                            applyFilters(answers.toList())
                        }
                    } else {
                        currentIndex.intValue += 1
                    }
                }
            },
            onSwipeRight = {
                if (currentIndex.intValue <= questions.size-1 ) {
                    answers[currentIndex.intValue] = currentQuestion.value.answer2
                    if (currentIndex.intValue == questions.size - 1) {
                        answers.toList().let {
                            applyFilters(answers.toList())
                        }

                    } else {
                        currentIndex.intValue += 1
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))
        MyButton(text = "Hemen Film Bul") {
            applyFilters(answers.toList())
        }


    }

}


@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeableCard(
    question: Question,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val anchors = mapOf(-300f to -1, 0f to 0, 300f to 1)
    val coroutineScope = rememberCoroutineScope()
    var offsetX by remember { mutableFloatStateOf(0f) }
    val swipeThreshold = 20.dp // Threshold for swiping

    // Track the offset to decide swipe action
    LaunchedEffect(swipeableState.offset.value) {
        val offset = swipeableState.offset.value
        if (offset > 300) {
            coroutineScope.launch {
                swipeableState.snapTo(targetValue = 0)
            }
        } else if (offset < -300) {
            coroutineScope.launch {
                swipeableState.snapTo(targetValue = 0)
            }
        }
    }

    // Derive text and color based on swipe offset
    val chooseText by remember(question, swipeableState.offset.value) {
        derivedStateOf {
            when {
                swipeableState.offset.value < -20 -> question.answer1
                swipeableState.offset.value > 20 -> question.answer2
                else -> "" // Default text when in the center
            }
        }
    }
    val color by remember {
        derivedStateOf {
            when {
                swipeableState.offset.value < -20 -> Color(0x8000FF00)
                swipeableState.offset.value > 20 -> Color(0x80FF0000)
                else -> Color(0xFF000000)// Default color when in the center
            }
        }
    }

    Box(
        modifier = Modifier
            .width(400.dp)
            .height(400.dp)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
            .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
            .graphicsLayer(rotationZ = swipeableState.offset.value * 0.05f) // Adjust rotation if needed
            .border(1.dp,MaterialTheme.colorScheme.secondary, shape =RoundedCornerShape(16.dp) )
            .background(
                color = color,
                shape = RoundedCornerShape(16.dp)
            )
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    var initialOffsetX = 0f
                    var dragOffsetX = 0f

                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.first()

                        when {
                            change.pressed -> {
                                // Save initial position
                                if (initialOffsetX == 0f) {
                                    initialOffsetX = change.position.x
                                }

                                // Calculate drag offset
                                dragOffsetX = change.position.x - initialOffsetX
                                offsetX = dragOffsetX
                            }

                            else -> {
                                // Determine if swiped enough to trigger an action
                                if (dragOffsetX > swipeThreshold.toPx()) {
                                    onSwipeRight()
                                } else if (dragOffsetX < -swipeThreshold.toPx()) {
                                    onSwipeLeft()
                                }

                                // Reset position
                                offsetX = 0f
                                initialOffsetX = 0f
                                dragOffsetX = 0f
                            }
                        }
                    }
                }
            }
            .padding(16.dp),
        Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = chooseText, style = MaterialTheme.typography.bodyMedium,color=MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = question.text, style = MaterialTheme.typography.headlineLarge,color=MaterialTheme.colorScheme.onBackground, textAlign = TextAlign.Center)

        }
    }
}

@Composable
fun MyButton(text:String,onclick:()-> Unit){
    OutlinedButton(
        onClick = { onclick() },
        border = ButtonDefaults.buttonBorder(borderStroke = BorderStroke(1.dp, Color(0xFFFFD700))),
        modifier = Modifier
            .width(200.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        Text(
            text = text,
            color = Color(0xFFFFD700),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }


}

