#version 440

in vec3 pass_position;
in float pass_isWater;

out vec4 fragColor;

void main()
{
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
	}
	else
	{**/
		/**if(pass_position.y > 8.0f && pass_position.y < 14.0f)
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
		}
		else if(pass_position.y > -4.0f && pass_position.y <= 0.0f)
		{
			vec4 smoothGreen = vec4(0.28f, 0.70f, 0.28f, 1.0f);
			fragColor = smoothGreen;
		}
		else
		{
			fragColor = vec4(0.05f, 0.075f, 0.05f, 1.0f);
		}**/
			fragColor = vec4(0.05f, 0.075f, 0.05f, 1.0f);
	//}
}