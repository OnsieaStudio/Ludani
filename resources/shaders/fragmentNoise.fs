#version 330

in vec3 pass_position;

layout(location=0) out vec4 outColor;

void main()
{
	vec4 smoothGrey = vec4(0.3f, 0.3f, 0.3f, 1.0f);
	if(pass_position.y < 21.0f)
	{
		vec4 smoothGreen = vec4(0.05f, 0.80f, 0.10f, 1.0f);
		float factor = pass_position.y / 21.0f;

		outColor = (smoothGreen * (1.0f-factor)) + (smoothGrey * factor) + vec4(0.1f, 0.1f, 0.1f, 1.0f);
		outColor = (outColor * 1.0f) + vec4(pass_position, 1.0f) / (vec4(100.0f, 42.0f, 100.0f, 1.0f) * 16.0f);
		outColor.w = 1.0f;
	}
	else
	{
		vec4 smoothWhite = vec4(0.75f, 0.75f, 0.75f, 1.0f);
		float factor = (pass_position.y - 21.0f) / (42.0f - 21.0f);

		outColor = (smoothGrey * (1.0f-factor)) + (smoothWhite * factor) + vec4(0.1f, 0.1f, 0.1f, 1.0f);
		outColor = (outColor * 1.0f) + vec4(pass_position, 1.0f) / (vec4(100.0f, 42.0f, 100.0f, 1.0f) * 16.0f);
		outColor.w = 1.0f;
	}


	

	//outColor = vec4(pass_position, 1.0f) / vec4(256.0f, 256.0f, 256.0f, 1.0f) * vec4(1.00f, 1.00f, 1.00f, 1.0f) * 2.0f;
	
	
		/**if(pass_isWater >= 0.5f)
	{
		float h = (pass_position.y * pass_position.y) / 10.0f;
		if(h <= 0)
		{
			h = 1.0f;
		}
		else
		{
			h /= 6.0f;
		}
		vec4 smoothBlue = vec4(0.28f, 0.25f, 0.70f, 1.0f);
		fragColor = ((vec4(h, h, h, 1.0f) * 1.5f) + (smoothBlue * 1.75f)) / (1.5f + 1.75f);
		fragColor.w = 1.0f;
	}
	else
	{
		if(pass_position.y > 8.0f && pass_position.y < 14.0f)
		{
			fragColor = vec4(pass_position.y / 14.0f, pass_position.y / 14.0f, pass_position.y / 14.0f, 1.0f);
		}
		else if(pass_position.y >= 14.0f)
		{
			fragColor = vec4(1.0f, 1.0f, 1.0f, 1.0f);
		}
		else if(pass_position.y > 2.0f && pass_position.y <= 8.0f)
		{
			fragColor = vec4(pass_position.y / 8.0f * 0.5f, pass_position.y / 8.0f * 0.5f, pass_position.y / 8.0f * 0.5f, 1.0f);
		}
		else if(pass_position.y > 0.0f && pass_position.y <= 2.0f)
		{
			vec4 smoothGreen = vec4(0.25f, 0.74f, 0.25f, 1.0f);
			vec4 white = vec4(pass_position.y / 8.0f * 0.5f, pass_position.y / 8.0f * 0.5f, pass_position.y / 8.0f * 0.5f, 1.0f);
			float factor = pass_position.y / 2.0f;
			
			fragColor = ((smoothGreen * (1.0f - factor)) + white * factor);
			fragColor.w = 1.0f;
		}
		else if(pass_position.y > -4.0f && pass_position.y <= 0.0f)
		{
			vec4 smoothGreen = vec4(0.28f, 0.70f, 0.28f, 1.0f);
			fragColor = smoothGreen;
		}
		else
		{
			vec4 smoothGreen = vec4(0.28f, 0.70f, 0.28f, 1.0f);
			fragColor = smoothGreen;
		}**/
}