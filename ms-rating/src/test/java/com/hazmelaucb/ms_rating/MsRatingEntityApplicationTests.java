package com.hazmelaucb.ms_rating;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hazmelaucb.ms_rating.api.RatingController;
import com.hazmelaucb.ms_rating.model.dto.RatingRequestDTO;
import com.hazmelaucb.ms_rating.bl.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

@WebMvcTest(RatingController.class)
@ExtendWith(MockitoExtension.class)
class MsRatingEntityApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RatingService ratingService;

	private RatingRequestDTO sampleRating;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		// Registro el módulo para manejar LocalDateTime
		objectMapper.registerModule(new JavaTimeModule());
		sampleRating = new RatingRequestDTO(1L, 5L, 10L, 100L, LocalDateTime.now(), LocalDateTime.now());
	}

	@Test
	void getAllRatings_ShouldReturnListOfRatings() throws Exception {
		List<RatingRequestDTO> ratings = Arrays.asList(sampleRating);
		when(ratingService.getAllRatings()).thenReturn(ratings);

		mockMvc.perform(get("/v1/rating")
						.contentType(MediaType.APPLICATION_JSON)
						.with(SecurityMockMvcRequestPostProcessors.user("user").password("password").roles("USER"))) // Simulando el usuario autenticado
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(sampleRating.getId().intValue())));
	}

	@Test
	void getRatingById_ShouldReturnRating() throws Exception {
		when(ratingService.getRatingById(1L)).thenReturn(sampleRating);

		mockMvc.perform(get("/v1/rating/1")
						.contentType(MediaType.APPLICATION_JSON)
						.with(SecurityMockMvcRequestPostProcessors.user("user").password("password").roles("USER"))) // Simulando el usuario autenticado
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(sampleRating.getId().intValue())));
	}

	@Test
	void createRating_ShouldReturnCreatedRating() throws Exception {
		when(ratingService.createRating(any(RatingRequestDTO.class))).thenReturn(sampleRating);

		String jsonContent = objectMapper.writeValueAsString(sampleRating);

		mockMvc.perform(post("/v1/rating")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonContent)
						.with(SecurityMockMvcRequestPostProcessors.user("user").password("password").roles("USER"))) // Simulando el usuario autenticado
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(sampleRating.getId().intValue())));
	}

	@Test
	void updateRating_ShouldReturnUpdatedRating() throws Exception {
		when(ratingService.updateRating(eq(1L), any(RatingRequestDTO.class))).thenReturn(sampleRating);

		String jsonContent = objectMapper.writeValueAsString(sampleRating);

		mockMvc.perform(put("/v1/rating/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonContent)
						.with(SecurityMockMvcRequestPostProcessors.user("user").password("password").roles("USER"))) // Simulando el usuario autenticado
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(sampleRating.getId().intValue())));
	}

	@Test
	void deleteRating_ShouldReturnNoContent() throws Exception {
		when(ratingService.deleteRating(1L)).thenReturn(true);

		mockMvc.perform(delete("/v1/rating/1")
						.with(SecurityMockMvcRequestPostProcessors.user("user").password("password").roles("USER"))) // Simulando el usuario autenticado
				.andExpect(status().isNoContent());
	}
}
